package com.learn.java.common.jvm;

/**
 * 死锁问题模拟
 *
 * @author lfq
 */
public class DeadLockDemo {

    private static final String bowl = "碗";
    private static final String chopsticks = "筷子";

    static class LockA implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "开始争抢");
                    synchronized (DeadLockDemo.bowl) {
                        System.out.println(Thread.currentThread().getName() + "争抢到碗");
                        synchronized (DeadLockDemo.chopsticks) {
                            System.out.println(Thread.currentThread().getName() + "争抢到筷子");
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "完成吃饭");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class LockB implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "开始争抢");
                    synchronized (DeadLockDemo.bowl) {
                        System.out.println(Thread.currentThread().getName() + "争抢到筷子");
                        synchronized (DeadLockDemo.chopsticks) {
                            System.out.println(Thread.currentThread().getName() + "争抢到碗");
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "完成吃饭");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(new LockA());
        Thread t2 = new Thread(new LockB());
        t1.start();
        t2.start();
    }
}
