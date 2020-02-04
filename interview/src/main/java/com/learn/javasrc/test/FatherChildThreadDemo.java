package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 父子线程
 *
 * @author lfq
 */
@Slf4j
public class FatherChildThreadDemo {

    private static ThreadLocal<Integer> localValue = new ThreadLocal<>();

    public static void setLocalValue(int value) {
        localValue.set(value);
    }

    /**
     * 子线程
     */
    public static class ChildThread implements Runnable {
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + ",is running...");
        }
    }

    public static void main(String[] args) {
        setLocalValue(5);
        Thread t = new Thread(new ChildThread());
        t.start();
    }
}
