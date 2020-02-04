package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 并发集合类(阻塞队列应用)
 *
 * @author lfq
 */
@Slf4j
public class ConcurrentTest {
    private static final LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();

    public static class Producer implements Runnable {
        private Integer value;

        public Producer(Integer value) {
            this.value = value;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    log.info(Thread.currentThread().getName() + ",put value:" + value);
                    blockingQueue.put(value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Integer value = blockingQueue.take();
                    log.info(Thread.currentThread().getName() + ",get value:" + value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        Thread[] producerThreads = new Thread[5];
        Thread[] consumerThreads = new Thread[10];

        for (int i = 0; i < producerThreads.length; i++) {
            producerThreads[i] = new Thread(new Producer(i));
            producerThreads[i].start();
        }

        for (int i = 0; i < consumerThreads.length; i++) {
            consumerThreads[i] = new Thread(new Consumer());
            consumerThreads[i].start();
        }
    }
}
