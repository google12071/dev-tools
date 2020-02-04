package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;

/**
 * 队列数据结构
 *
 * @author lfq
 */
@Slf4j
public class QueueTest {
    private static final SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);

    public static class TakeThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Integer value = queue.take();
                    log.info(Thread.currentThread().getName() + ",take value:" + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        try {
            Thread thread = new TakeThread();
            thread.setName("take线程");
            thread.start();
            int i = 0;
            while (i<1000) {
                queue.put(i++);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
