package com.learn.java.common.javabase.concurrent.util.basic;

import com.learn.java.common.javabase.concurrent.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 线程安全问题,多线程并发更新自增序号的值(synchronized关键字保证共享变量同一时刻仅能被一个线程更新)
 */
public class ThreadSafeDemo {

    private static CopyOnWriteArrayList<String> requestIdList = new CopyOnWriteArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Tools.class);

    public static void main(String[] args) {
        int threadNums = 100;
        int requestCount = 5;
        Thread[] threads = new Thread[threadNums];
        for (int i = 0; i < threadNums; i++) {
            threads[i] = new Thread(new Worker(requestCount));
        }

        for (Thread t : threads) {
            t.start();
        }

        //等待线程执行完毕
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (!requestIdList.isEmpty()) {
            requestIdList.forEach(System.out::println);
        }
    }

    public static class Worker implements Runnable {

        private int requestCount;

        public Worker(int requestCount) {
            this.requestCount = requestCount;
        }

        @Override
        public void run() {
            int i = requestCount;
            RequestIdGenerator generator = RequestIdGenerator.getGenerator();
            String requestId;
            while (i-- > 0) {
                requestId = generator.nextRequestId();

                if (!requestIdList.contains(requestId)) {
                    requestIdList.add(requestId);
                }

                processRequest(requestId);
            }
        }

        private void processRequest(String requestId) {
            Tools.randomPause(50);
            System.out.printf("%s process requestID: %s %n", Thread.currentThread().getName(), requestId);
        }
    }
}
