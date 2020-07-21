package com.learn.java.common.zk.lock;

/**
 * @ClassName ZkDistributeAdvanceLock
 * @Description:
 * @Author lfq
 * @Date 2020/7/21
 **/

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 优化后的分布式锁:
 * 利用临时顺序节点来实现分布式锁
 * 获取锁：取顺序号（类似银行排队拿号），判断自己的序号是否最小，若是则获得锁，否则注册前一个兄弟节点watcher，则阻塞等待
 * 释放锁：删除自己创建的临时顺序节点
 */
public class ZkDistributeAdvanceLock implements Lock {
    private static final Logger logger = LoggerFactory.getLogger(ZkDistributeAdvanceLock.class);

    private final CountDownLatch downLatch = new CountDownLatch(1);


    private String path;

    private ZkClient zkClient;

    /**
     * 当前路径
     */
    private ThreadLocal<String> currentPath = new ThreadLocal<>();

    /**
     * 前一个兄弟节点路径
     */
    private ThreadLocal<String> beforePath = new ThreadLocal<>();

    /**
     * 可重入计数
     */
    private ThreadLocal<Integer> reenterCount = ThreadLocal.withInitial(() -> 0);

    public ZkDistributeAdvanceLock(String path) {
        if (path == null || "".equals(path.trim())) {
            throw new IllegalArgumentException("路径为空!");
        }

        this.path = path;

        zkClient = new ZkClient("localhost:2181");

        zkClient.setZkSerializer(new CustomSerializer());

        try {
            if (!zkClient.exists(path)) {
                zkClient.createPersistent(path, true);
            }
        } catch (ZkNodeExistsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        //尝试获取分布式锁，若获取不到则等待，监听到上一个兄弟节点删除后，再次尝试获取锁
        if (!tryLock()) {
            //阻塞等待
            waitForLock();
            //再次竞争分布式锁
            lock();
        }
    }

    @Override
    public void lockInterruptibly() {
    }

    @Override
    public boolean tryLock() {
        logger.info(Thread.currentThread().getName() + "尝试获取分布式锁...");

        //先创建临时顺序节点
        if (currentPath.get() == null || !zkClient.exists(currentPath.get())) {
            String node = zkClient.createEphemeralSequential(path + "/", "locked");
            currentPath.set(node);
            reenterCount.set(0);
        }

        //获取所有节点
        List<String> childList = zkClient.getChildren(path);

        //所有子节点排序
        Collections.sort(childList);

        //若是最小节点，可获得锁
        if (currentPath.get().equals(path + "/" + childList.get(0))) {
            //重入计数+1
            reenterCount.set(reenterCount.get() + 1);
            logger.info(Thread.currentThread().getName() + "获得分布式锁...");
            return true;

        } else {
            //获取上一个兄弟节点
            int currentIndex = childList.indexOf(currentPath.get().substring(path.length() + 1));
            String node = path + "/" + childList.get(currentIndex - 1);
            beforePath.set(node);
            logger.info(Thread.currentThread().getName() + "获取分布式锁失败...");
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        return false;
    }

    @Override
    public void unlock() {
        logger.info(Thread.currentThread() + "释放当前分布式锁");
        if (reenterCount.get() > 1) {
            //重入次数-1，释放锁
            reenterCount.set(reenterCount.get() - 1);
            return;
        }

        //删除节点
        if (currentPath.get() != null) {
            zkClient.delete(currentPath.get());
            currentPath.set(null);
            reenterCount.set(0);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private void waitForLock() {
        //监听上一个兄弟节点数据变更
        IZkDataListener dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                logger.info(Thread.currentThread().getName() + "监听到节点变更");
                downLatch.countDown();
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                logger.info(Thread.currentThread().getName() + "监听到节点删除");
            }
        };

        zkClient.subscribeDataChanges(beforePath.get(), dataListener);

        //阻塞自己
        if (zkClient.exists(beforePath.get())) {
            logger.info(Thread.currentThread().getName() + "分布式锁没有抢到，进入阻塞状态");
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //取消监听
        zkClient.unsubscribeDataChanges(beforePath.get(), dataListener);
    }
}
