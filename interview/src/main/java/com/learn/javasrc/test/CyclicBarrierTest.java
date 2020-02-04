package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * CyclicBarrier测试
 *
 * @author lfq
 */
@Slf4j
public class CyclicBarrierTest {

    private static final CyclicBarrier barrier = new CyclicBarrier(10, () -> {
        log.info("all threads is ready...");
    });


    /**
     * 工作线程
     */
    public static class Worker implements Runnable {
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + ",ready...");
            try {
                barrier.await();
                Thread.sleep(new Random().nextInt(10) + 1);
                log.info(Thread.currentThread().getName() + ",done...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread[] workers = new Thread[10];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(new Worker());
            workers[i].start();
        }
    }
}
