package com.learn.java.common.javabase.concurrent.util;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch实例内部维护一个先决条件条件的数量，当等于0时，调用countDown（）方法不会跑出异常，但调用await（）方法的线程也不再等待
 * 综上所述，同一个countDownLatch实例协调线程仅能使用1次,
 */
public class CountDownLatchExample {

    private static final CountDownLatch latch = new CountDownLatch(4);

    private static int data;

    public static void main(String[] args) {
        Thread workerThread = new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                data = i;
                System.out.println("thread:" + Thread.currentThread().getName() + ",data:" + data);

                latch.countDown();
                // 使当前线程暂停（随机）一段时间
                ThreadTools.randomPause(3000);
                System.out.println("Thread:" + Thread.currentThread().getName() + ",invoke countDown done");
            }
        }, "test");
        workerThread.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("thread:" + Thread.currentThread().getName() + ",data:" + data);

    }
}
