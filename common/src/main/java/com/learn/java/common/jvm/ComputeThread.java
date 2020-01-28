package com.learn.java.common.jvm;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 计算线程，CPU密集型
 */
public class ComputeThread implements Runnable {
    private static final Random r = new Random();

    @Override
    public void run() {
        while (true) {
            int value = r.nextInt(100);
            System.out.println(Thread.currentThread().getName() + "compute:" + value);
        }
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService service = new ThreadPoolExecutor(10, 15, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        for (int i = 0; i < 10; i++) {
            service.submit(new ComputeThread());
        }
    }
}
