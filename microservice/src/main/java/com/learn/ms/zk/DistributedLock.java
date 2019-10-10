package com.learn.ms.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Zookeeper实现分布式锁
 */
public class DistributedLock {

    public static void main(String[] args) {

        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.200.56:2181", policy);

        client.start();

        //创建分布式锁
        InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");

        boolean flag = false;
        try {
            //尝试获取锁，最多等待5秒
            flag = mutex.acquire(5, TimeUnit.SECONDS);
            if (flag) {
                System.out.println("Thread" + Thread.currentThread().getName() + ",分布式锁获取成功，并执行业务....");
            } else {
                System.out.println("Thread" + Thread.currentThread().getName() + ",分布式锁获取失败....");
            }

            Thread.sleep(4000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (flag) {
                try {
                    mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        client.close();
    }
}
