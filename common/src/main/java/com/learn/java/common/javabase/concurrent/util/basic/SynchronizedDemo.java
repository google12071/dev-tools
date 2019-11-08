package com.learn.java.common.javabase.concurrent.util.basic;

/**
 * 线程同步
 */
public class SynchronizedDemo {

    /**
     * 共享变量
     */
    private boolean ready = false;
    private int result = 0;
    private int number = 1;


    public void read() {
        if (ready) {
            result = number * 3;
        }
        System.out.println("Thread:" + Thread.currentThread().getName() + ",result的值为：" + result);
    }

    public void write() {
        ready = true;
        number = 2;
        System.out.println("Thread:" + Thread.currentThread().getName() + ",number的值为：" + number);
    }

    /**
     * 内部线程类
     */

    private class ReadWriteThread extends Thread {
        private boolean flag = false;

        public ReadWriteThread(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            if (flag) {
                read();
            } else {
                write();
            }
        }
    }

    public static void main(String[] args) {

        SynchronizedDemo synDemo = new SynchronizedDemo();
        //启动线程执行写操作
        synDemo.new ReadWriteThread(false).start();
        //启动线程执行读操作
        synDemo.new ReadWriteThread(true).start();
    }
}
