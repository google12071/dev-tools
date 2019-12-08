package com.learn.java.common.javabase.concurrent.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏工具类实现线程间协作(最后一个线程执行await方法时，唤醒barrier实例上的所有线程)
 */
public class CyclicBarrierDemo {

    private final static CyclicBarrier barrier = new CyclicBarrier(5);

    static class WorkThread implements Runnable {
        private Integer threadSeq;

        private WorkThread(Integer threadSeq) {
            this.threadSeq = threadSeq;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ",arrived,time:" + System.currentTimeMillis());
                barrier.await();
                //模拟耗时操作
                ThreadTools.randomPause(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ",finish,time:" + System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new WorkThread(i), "线程" + i);
            threads[i].start();
        }
    }
}
