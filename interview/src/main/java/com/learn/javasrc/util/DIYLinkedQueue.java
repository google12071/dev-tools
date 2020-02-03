package com.learn.javasrc.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于链表的自定义队列
 *
 * @author lfq
 */
@Slf4j
public class DIYLinkedQueue<T> implements DIYQueue<T> {

    /**
     * 队列的大小，使用 AtomicInteger 来保证其线程安全
     */
    private AtomicInteger size = new AtomicInteger(0);

    /**
     * 队列容量
     */
    private final Integer capacity;

    /**
     * 放数据锁
     */
    private ReentrantLock putLock = new ReentrantLock();

    /**
     * 拿数据锁
     */
    private ReentrantLock takeLock = new ReentrantLock();

    /**
     * 队列头
     */
    private volatile Node<T> head;

    /**
     * 队列尾
     */
    private volatile Node<T> tail;

    /**
     * 自定义队列元素
     */
    class DIYNode extends Node<T> {
        public DIYNode(T item) {
            super(item);
        }
    }

    /**
     * 有参构造器
     *
     * @param capacity
     */
    public DIYLinkedQueue(Integer capacity) {
        // 进行边界的校验
        if (null == capacity || capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        head = tail = new DIYNode(null);
    }

    /**
     * 无参构造器
     */
    public DIYLinkedQueue() {
        capacity = Integer.MAX_VALUE;
        head = tail = new Node<>(null);
    }

    @Override
    public boolean put(T item) {
        // 禁止空数据
        if (null == item) {
            return false;
        }
        try {
            // 尝试加锁，500 毫秒未获得锁直接被打断
            boolean lockSuccess = putLock.tryLock(500, TimeUnit.MILLISECONDS);
            if (!lockSuccess) {
                return false;
            }
            // 校验队列大小
            if (size.get() >= capacity) {
                log.info("queue is full");
                return false;
            }
            // 追加到队尾
            tail = tail.next = new DIYNode(item);
            // 计数
            size.incrementAndGet();
            return true;
        } catch (InterruptedException e) {
            log.info("tryLock 500 timeOut", e);
            return false;
        } catch (Exception e) {
            log.error("put error", e);
            return false;
        } finally {
            putLock.unlock();
        }
    }

    @Override
    public T take() {
        // 队列是空的，返回 null
        if (size.get() == 0) {
            return null;
        }
        try {
            // 拿数据我们设置的超时时间更短
            boolean lockSuccess = takeLock.tryLock(300, TimeUnit.MILLISECONDS);
            if (!lockSuccess) {
                throw new RuntimeException("加锁失败");
            }
            // 把头结点的下一个元素拿出来
            Node expectHead = head.next;
            // 把头结点的值拿出来
            T result = head.data;
            // 把头结点的值置为 null，帮助 gc
            head.data = null;
            // 重新设置头结点的值
            head = (DIYNode) expectHead;
            size.decrementAndGet();
            // 返回头结点的值
            return result;
        } catch (InterruptedException e) {
            log.info(" tryLock 200 timeOut", e);
        } catch (Exception e) {
            log.info(" take error ", e);
        } finally {
            takeLock.unlock();
        }
        return null;
    }
}
