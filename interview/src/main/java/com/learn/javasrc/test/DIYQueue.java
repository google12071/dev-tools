package com.learn.javasrc.test;

/**
 * 手写队列
 *
 * @param <T>
 * @author lfq
 */
public interface DIYQueue<T> {

    /**
     * 放数据
     *
     * @param item 入参
     * @return true 成功、false失败
     */
    boolean put(T item);

    /**
     * 取数据
     *
     * @return 泛型数据
     */
    T take();

    /**
     * 队列中元素基本结构
     *
     * @param <T>
     */
    class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }
}
