package com.learn.java.common.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * map数据结构测试
 *
 * @author lfq
 */
public class MapTest {
    private static final HashMap<String, String> map = new HashMap<>();

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static class UUIDThread implements Runnable {
        private int i;

        public UUIDThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ", await...");
                countDownLatch.await(5, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String uuid = UUID.randomUUID().toString();
            String threadNum = String.valueOf(i);
            System.out.println(Thread.currentThread().getName() + ",uuid:" + uuid + ",threadNum:" + threadNum);
            map.put(UUID.randomUUID().toString(), String.valueOf(i));
        }
    }

    public static void multipleThread() {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(new UUIDThread(i));
        }
        for (int i = 0; i < 100; i++) {
            threads[i].start();
        }
        countDownLatch.countDown();
        System.out.println(Thread.currentThread().getName() + "countDown...");
    }

    public static void concurrentHashMapTest() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put(null, 3);
        map.put("c", null);
        map.entrySet().forEach(System.out::println);
    }

    public static void main(String[] args) {
        concurrentHashMapTest();
    }
}
