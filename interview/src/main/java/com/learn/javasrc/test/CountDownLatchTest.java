package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch测试
 *
 * @author lfq
 */
@Slf4j
public class CountDownLatchTest {

    private static final CountDownLatch start = new CountDownLatch(1);


    /**
     * 工作线程
     */
    public static class Worker implements Runnable {
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + ",await...");
            try {
                start.await();
                Thread.sleep(new Random().nextInt(10) + 1);
                log.info(Thread.currentThread().getName() + ",done...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Thread[] workers = new Thread[10];
            for (int i = 0; i < workers.length; i++) {
                workers[i] = new Thread(new Worker());
                workers[i].start();
            }
            log.info(Thread.currentThread().getName() + ",awaiting...");
            log.info(Thread.currentThread().getName() + ",done...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            start.countDown();
        }
    }
}
