package com.learn.java.common.javabase.concurrent.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName CyclicBarrierDemo
 * @Description:栅栏
 * @Author lfq
 * @Date 2020/2/16
 **/
@Slf4j
public class CyclicBarrierDemo {
    private static final CyclicBarrier barrier = new CyclicBarrier(10, new Runnable() {
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + ",finish,parties:{}", barrier.getNumberWaiting());
        }
    });

    public static class PartiesThread implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await(5, TimeUnit.SECONDS);
                Thread.sleep(1000 * new Random().nextInt(5) + 1);
                log.info(Thread.currentThread().getName() + ",arrived,parties:{}", barrier.getNumberWaiting());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new PartiesThread());
            t.start();
        }
    }
}
