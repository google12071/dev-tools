package com.learn.java.common.javabase.concurrent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 线程时间模拟工具类
 */
public class ThreadTools {
    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(ThreadTools.class);


    /**
     * 线程随机中断一段时间
     * @param maxPauseTime
     */
    public static void randomPause(int maxPauseTime) {
        try {
            int sleepTime=random.nextInt(maxPauseTime);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程随机中断一段时间
     * @param minPauseTime
     * @param maxPauseTime
     *
     */
    public static void randomPause(int minPauseTime,int maxPauseTime) {
        try {
            int sleepTime = minPauseTime == maxPauseTime ? minPauseTime : random.nextInt(maxPauseTime - minPauseTime) + minPauseTime;
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
