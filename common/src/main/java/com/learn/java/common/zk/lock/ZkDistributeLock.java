package com.learn.java.common.zk.lock;

/**
 * @ClassName ZkDistributeLock
 * @Description:
 * @Author lfq
 * @Date 2020/7/21
 **/

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于zk实现的分布式锁
 */
public class ZkDistributeLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(ZkDistributeLock.class);

    private final CountDownLatch downLatch = new CountDownLatch(1);

    private String path;

    private ZkClient zkClient;

    public ZkDistributeLock(String path) {
        if (path == null || path.trim().equals("")) {
            throw new IllegalArgumentException("路径为空!");
        }
        this.path = path;

        zkClient = new ZkClient("localhost:2181");

        zkClient.setZkSerializer(new CustomSerializer());
    }

    @Override
    public void lock() {
        //尝试加锁失败，则阻塞等待
        if (!tryLock()) {
            //没有获得锁，当前线程阻塞
            waitForLock();
            //从等待中被唤醒，再次尝试加锁
            lock();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            zkClient.createEphemeral(path);
        } catch (ZkNodeExistsException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        zkClient.delete(path);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private void waitForLock() {

        //设置节点监听
        IZkDataListener dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                logger.info(s + ",监听节点数据发生变动,data:" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                logger.info("监听节点被删除，可重新尝试加锁");

                //唤醒阻塞线程
                downLatch.countDown();
            }
        };
        zkClient.subscribeDataChanges(path, dataListener);

        //阻塞当前线程
        try {
            if (zkClient.exists(path)) {
                downLatch.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //取消监听
        zkClient.unsubscribeDataChanges(path, dataListener);
    }
}

