package com.learn.java.common.designPattern;

/**
 * 基于静态内部类的单例模式
 */
public class StaticHolderSingleton {
    /**
     * 构造器私有化确保类实例唯一性
     */
    private StaticHolderSingleton() {
    }

    /**
     * 类初始化时被创建
     */
    static class InnerClass {
        final static StaticHolderSingleton INSTANCE = new StaticHolderSingleton();
    }

    public static StaticHolderSingleton getInstance() {
        return InnerClass.INSTANCE;
    }
}
