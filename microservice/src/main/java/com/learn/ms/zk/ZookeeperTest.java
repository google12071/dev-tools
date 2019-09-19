package com.learn.ms.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * java连接zookeeper测试类
 */
public class ZookeeperTest implements Watcher {

    private ZooKeeper zooKeeper;

    private final int SESSION_TIME_OUT = 2000;

    private Logger logger = LoggerFactory.getLogger(ZookeeperTest.class);

    private CountDownLatch downLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            logger.info("watch received event");
            downLatch.countDown();
        }

    }

    public void connectZookeepr(String host) throws Exception {
        zooKeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
        downLatch.await();
        logger.info("zookeeper connect success");
    }

    public void closeZookeeper() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
            logger.info("zookeeper close success");
        }
    }

    public void createNode(String path, String data) throws KeeperException, InterruptedException {
        zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getChildren(path, false);
    }

    public String getData(String path) throws KeeperException, InterruptedException {
        byte[] bytes = zooKeeper.getData(path, false, null);
        return bytes == null ? null : new String(bytes);
    }

    public Stat setData(String path, String data) throws InterruptedException, KeeperException {
        return zooKeeper.setData(path, data.getBytes(), -1);
    }

    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, -1);
    }

    public String getCTime(String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, false);
        return String.valueOf(stat.getCtime());
    }

    public Integer getChildrenNum(String path) throws InterruptedException, KeeperException {
        return zooKeeper.getChildren(path, false).size();
    }


    public static void main(String[] args) {
        try {
            ZookeeperTest base = new ZookeeperTest();
            base.connectZookeepr("127.0.0.01:2181");

            List<String> children = base.getChildren("/");
            System.out.println("/:children" + children);

            base.createNode("/test", "hello world");
            String data = base.getData("/test");
            System.out.println("data:" + data);
            base.setData("/test", "welcome to china");

            String cTime = base.getCTime("/test");
            System.out.println("cTime:" + cTime);


            base.createNode("/hello", "zookeeper");

            System.out.println("/:children" + base.getChildren("/"));

            System.out.println("childrenNum:" + base.getChildrenNum("/"));

            base.deleteNode("/hello");

            System.out.println("/:children" + base.getChildren("/"));

            System.out.println("childrenNum:" + base.getChildrenNum("/"));

            base.closeZookeeper();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
