package com.learn.java.common.javabase.concurrent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 士兵射击训练模拟
 */
public class ShotPractice {

    private static final Logger logger = LoggerFactory.getLogger(ShotPractice.class);

    /**
     * 参与射击训练的士兵矩阵
     */

    private Soldier[][] soldierMatrix;

    /**
     * 每列士兵数量
     */
    private int column;

    /**
     * 射击持续时长
     */
    private int lasting;

    /**
     * 标记下一轮射击的士兵排数
     */
    volatile int nextLine = 0;

    /**
     * 标记射击是否完成【每排士兵全部完成射击时，标记为true】
     */
    volatile boolean done=false;

    final CyclicBarrier shiftBarrier;

    final CyclicBarrier startBarrier;

    public ShotPractice(int column, int lasting, final int row) {
        this.column = column;
        this.lasting = lasting;
        this.soldierMatrix = new Soldier[row][column];

        //循环为士兵队列编号
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                soldierMatrix[i][j] = new Soldier(i * column + j);
            }
        }

        shiftBarrier = new CyclicBarrier(column, () -> {
            nextLine = (nextLine + 1) % row;
            logger.info("下一次射击轮到第"+nextLine + "排士兵上场射击");
        });

        startBarrier = new CyclicBarrier(column);
    }

    /**
     * 士兵
     */
    static class Soldier {
        private int seqNo;//序号

        public Soldier(int seqNo) {
            this.seqNo = seqNo;
        }

        public void fire() {
            logger.info(this + "士兵开始射击...");
            ThreadTools.randomPause(5);
            logger.info(this + "士兵完成射击...");
        }

        @Override
        public String toString() {
            return "Soldier-" + seqNo;
        }
    }

    private void start() {
        //创建并启动工作者线程
        Thread[] threads = new Thread[column];
        for (int i = 0; i < column; i++) {
            threads[i] = new ShootingWorker(i);
            threads[i].start();
        }
        //暂停指定时间
        ThreadTools.randomPause(lasting * 1000);

        stop();

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("射击训练结束");
    }

    private void stop() {
        done = true;
    }


    /**
     * 射击工作线程
     */
    class ShootingWorker extends Thread {

        private final int index;

        public ShootingWorker(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            Soldier soldier;
            try {
                while (!done) {

                    soldier = soldierMatrix[nextLine][index];

                    // 一排中的士兵必须同时开始射击
                    startBarrier.await();

                    //士兵开始射击
                    soldier.fire();

                    // 一排中的士兵必须等待该排中的所有其他士兵射击完毕才能够离开射击点
                    shiftBarrier.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ShotPractice practice = new ShotPractice(5,3 , 3);
        practice.start();
    }
}
