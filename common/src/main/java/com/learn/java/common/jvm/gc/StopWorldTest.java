package com.learn.java.common.jvm.gc;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @ClassName StopWorldTest
 * @Description: Stop-The-Word gc时，应用程序出现卡顿测试
 * @Author lfq
 * @Date 2020/2/7
 **/
@Slf4j
public class StopWorldTest {
    /**
     * 内存分配线程，不断分配内存，引起GC，造成应用程序卡顿
     */
    public static class AllocateThread implements Runnable {
        private Map<Long, byte[]> bytesMap = Maps.newConcurrentMap();

        @Override
        public void run() {
            while (true) {
                try {
                    log.info(Thread.currentThread().getName() + "," + "allocate memory");
                    //超过900M，清空内存，防止溢出
                    if (bytesMap.size() * 512 / 1024 / 1024 >= 900) {
                        bytesMap.clear();
                        log.info("clear map");
                    }
                    byte[] b;
                    for (int i = 0; i < 10000; i++) {
                        bytesMap.put(System.currentTimeMillis(), new byte[512*1024]);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印线程
     */
    public static class PrintThread implements Runnable {
        private static final long startTime = System.currentTimeMillis();

        @Override
        public void run() {
            while (true) {
                try {
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread allocateThread = new Thread(new AllocateThread());
        Thread printThread = new Thread(new PrintThread());
        allocateThread.start();
        printThread.start();
    }
}
