package com.learn.java.common.javabase.concurrent.basic;

/**
 * 常用线程API测试类
 */
public class ThreadAPITest {

    public static void join(long timeout) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        t.join(timeout);
    }

    public static void main(String[] args) {
        try {
            System.out.println(Thread.currentThread().getName() + ",wait");
            join(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ",finish");
    }
}
