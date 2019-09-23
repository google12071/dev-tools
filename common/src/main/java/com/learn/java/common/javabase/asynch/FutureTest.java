package com.learn.java.common.javabase.asynch;

/**
 * java8以前，使用Future实现异步执行任务
 */
public class FutureTest {

    public Double getValue() {
        return null;
    }

    public void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
