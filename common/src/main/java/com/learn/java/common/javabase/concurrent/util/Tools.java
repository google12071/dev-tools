package com.learn.java.common.javabase.concurrent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Tools {
    private static final Random rnd = new Random();
    private static final Logger logger = LoggerFactory.getLogger(Tools.class);

    public static void randomPause(int maxPauseTime) {
        int sleepTime = rnd.nextInt(maxPauseTime);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void startAndWaitTerminated(Iterable<Thread> threads)
            throws InterruptedException {
        if (null == threads) {
            throw new IllegalArgumentException("threads is null!");
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }
}
