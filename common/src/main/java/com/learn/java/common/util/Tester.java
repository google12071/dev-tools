package com.learn.java.common.util;

public class Tester {

    public static class ChildThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ",is running...");
        }
    }

    public static class ChildThread2 implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ",is running...");
        }
    }

    public static void main(String[] args) {

        Thread t = new Thread(new ChildThread2());
        t.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "is running");
    }
}
