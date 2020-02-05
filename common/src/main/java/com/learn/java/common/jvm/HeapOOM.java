package com.learn.java.common.jvm;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HeapOOM
 * @Description: 堆内存OOM演示
 * @Author lfq
 * @Date 2020/2/5
 **/
@Slf4j
public class HeapOOM {

    private static int threadNum = 0;

    public void doSomething() {
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final HeapOOM stackOOM = new HeapOOM();
        try {
            while (true) {
                threadNum++;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        stackOOM.doSomething();
                    }
                });
                thread.start();
            }
        } catch (Throwable e) {
            System.out.println("目前活动线程数量：" + threadNum);
            throw e;
        }
    }
}
