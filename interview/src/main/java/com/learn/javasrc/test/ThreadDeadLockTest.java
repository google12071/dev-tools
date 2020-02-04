package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 线程死锁模拟
 *
 * @author lfq
 */
@Slf4j
public class ThreadDeadLockTest {
    /**
     * 共享资源A
     */
    private static final Object resourceA = new Object();

    /**
     * 共享资源B
     */
    private static final Object resourceB = new Object();

    @Test
    public void deadLock() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (resourceA) {
                    log.info(Thread.currentThread().getName() + ",apply resourceA");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (resourceB) {
                        log.info(Thread.currentThread().getName() + ",apply resourceB");
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (resourceB) {
                    log.info(Thread.currentThread().getName() + ",apply resourceB");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (resourceA) {
                        log.info(Thread.currentThread().getName() + ",apply resourceA");
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(10000);
    }
}
