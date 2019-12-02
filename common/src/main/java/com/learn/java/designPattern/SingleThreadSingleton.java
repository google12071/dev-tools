package com.learn.java.designPattern;

/**
 * 单线程下的单例模式
 */
public class SingleThreadSingleton {

    /**
     * 保证类实例的唯一性
     */
    private static SingleThreadSingleton instance;

    /**
     * 构造器私有化
     */
    private SingleThreadSingleton() {
    }

    public static SingleThreadSingleton getInstance() {
        return instance == null ? new SingleThreadSingleton() : instance;
    }
}
