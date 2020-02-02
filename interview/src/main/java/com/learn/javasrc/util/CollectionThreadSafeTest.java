package com.learn.javasrc.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 集合线程安全测试
 *
 * @author lfq
 */
public class CollectionThreadSafeTest {
    private static List<Integer> numList = new CopyOnWriteArrayList<>();

    private static CountDownLatch latch = new CountDownLatch(1);

    /**
     * 修改集合元素线程
     */
    public static class ModifyThread extends Thread {
        private String name;
        private Integer value;

        public ModifyThread(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ",await modify");
                latch.await(5, TimeUnit.SECONDS);
                numList.add(value);
                System.out.println(Thread.currentThread().getName() + ",modify success,size:" + numList.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void buildThreadPool() {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService service = new ThreadPoolExecutor(10, 15, 5,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), factory);
        for (int i = 0; i < 10; i++) {
            service.submit(new ModifyThread(String.valueOf(i), i));
        }
    }

    public static void main(String[] args) {
        int size = 0;
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] = new ModifyThread(String.valueOf(i), 1);
        }
        for (int i = 0; i < 1000; i++) {
            threads[i].start();
        }
        latch.countDown();
        size = numList.size();
        System.out.println("最终的元素总个数:" + size);
    }
}
