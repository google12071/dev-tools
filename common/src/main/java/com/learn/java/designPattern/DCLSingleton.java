package com.learn.java.designPattern;

/**
 * 基于双重检查锁定的单例模式
 */
public class DCLSingleton {

    /**
     * 使用volatile修饰，确保可见性和有序性
     */
    private static volatile DCLSingleton instance;

    /**
     * 构造器私有化确保类实例唯一性
     */
    private DCLSingleton() {
    }

    /**
     * 获取单例实例
     * @return
     */
    public DCLSingleton getInstance() {
        //第一次检查确保仅当instance为空时，才进行同步
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    return new DCLSingleton();
                }
            }
            return instance;
        }
        return instance;
    }
}
