package com.learn.java.common.javabase.concurrent.util.basic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VolatileDemo {

    private Lock lock = new ReentrantLock();

    private volatile int number = 0;

    public int getNumber(){
        return this.number;
    }

    public void increase() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.number++;
    }

    public static void main(String[] args) {
        final VolatileDemo volDemo = new VolatileDemo();
        //开启500个线程，自增number变量
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    volDemo.increase();
                }
            }).start();
        }

        //如果还有子线程在运行，主线程就让出CPU资源，
        //直到所有的子线程都运行完了，主线程再继续往下执行
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println("number : " + volDemo.getNumber());
    }

}