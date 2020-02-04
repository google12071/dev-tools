package com.learn.javasrc.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池测试类
 *
 * @author lfq
 */
@Slf4j
public class ThreadPoolTest {
    private static final ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("thread-runner-%d").build();

    private static volatile int value = 0;

    /**
     * 工作者线程
     */
    public static class Worker implements Runnable {
        @Override
        public void run() {
            increment();
            log.info(Thread.currentThread().getName() + ",incremented,value:" + value);
        }
    }

    /**
     * value自增运算
     */
    public static void increment() {
        value++;
    }

    /**
     * 获取value值
     *
     * @return
     */
    public static int getValue() {
        return value;
    }

    /**
     * 自定义线程池
     */
    public static class CustomThreadPoolExecutor extends ThreadPoolExecutor {
        public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            log.info("hello," + t.getName() + ",before execute");
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            log.info("after execute");
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new CustomThreadPoolExecutor(3, 3, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), factory, new ThreadPoolExecutor.AbortPolicy());
        try {
            for (int i = 0; i < 100; i++) {
                executor.submit(new Worker());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        log.info("increment result:" + getValue());
    }
}
