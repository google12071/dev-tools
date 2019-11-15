package com.learn.java.common.javabase.concurrent.util.basic;

import com.learn.java.common.javabase.concurrent.util.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * 基于CAS原理的计数器
 */
public class CASBaseCounter {

    /**
     * 这里使用AtomicLongFieldUpdater只是出于演示目的，
     * 实际上更多的情况是我们不使用AtomicLongFieldUpdater，而是使用java.util.concurrent.atomic包下的类。
     */
    private final AtomicLongFieldUpdater<CASBaseCounter> updater;

    public CASBaseCounter() {
        updater = AtomicLongFieldUpdater.newUpdater(CASBaseCounter.class, "count");
    }

    /**
     * CAS原理仅能保证共享变量的原子性但不能保证可见性，为保证可见性，采用volatile修饰
     */
    private volatile long count;

    /**
     * 变量自增方法，采用CAS原理实现
     *
     * @return
     */
    public void increment() {
        long oldValue;
        long newValue;

        do {
            oldValue = count;
            newValue = oldValue + 1;
            System.out.println(Thread.currentThread().getName() + ",更新失败，再次重试");
        } while (!compareAndSwap(oldValue, newValue));
    }

    public long getValue() {
        return count;
    }

    /**
     * 先判断当前值有没有被其他线程修改过，若没有修改则更新成功，否则失败重试
     *
     * @param oldValue
     * @param newValue
     * @return
     */
    private boolean compareAndSwap(long oldValue, long newValue) {
        return updater.compareAndSet(this, oldValue, newValue);
    }

    public static void main(String[] args) {
        final CASBaseCounter counter = new CASBaseCounter();
        List<Thread> threadSet = new ArrayList<>();

        //创建20个线程并发更新
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tools.randomPause(50);
                    counter.increment();
                    System.out.println(Thread.currentThread().getName() + ",更新值成功!,更新后value:" + counter.getValue());
                }
            });
            threadSet.add(t);
        }

        //创建10个线程并发读取
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Tools.randomPause(30);
                    System.out.println(Thread.currentThread().getName() + ",读取值成功!,读取的value:" + counter.getValue());
                }
            });
            threadSet.add(t);
        }

        try {
            Tools.startAndWaitTerminated(threadSet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("final count:" + counter.getValue());
    }
}
