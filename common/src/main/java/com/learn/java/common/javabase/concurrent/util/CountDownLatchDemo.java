package com.learn.java.common.javabase.concurrent.util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 倒计时协调器countDownLatch使用案例(模拟多线程并发)
 */
public class CountDownLatchDemo {

    private static final CountDownLatch mainLatch = new CountDownLatch(10);
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        ExecutorService pool = Executors.newScheduledThreadPool(15);
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ",arrived...");
                try {
                    mainLatch.countDown();
                    //子线程准备主线程命令
                    latch.await(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ",produceRandom:" + new Random().nextInt(100) + 1 + ",now:" + System.currentTimeMillis());

                ThreadTools.randomPause(1, 3);
            });
        }

        //主线程等待子线程全部到达
        try {
            mainLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown();
        System.out.println(Thread.currentThread().getName() + ",countDown done...");

    }
}
