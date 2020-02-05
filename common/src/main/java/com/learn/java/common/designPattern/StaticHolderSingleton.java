package com.learn.java.common.designPattern;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于静态内部类的单例模式
 */
@Slf4j
public class StaticHolderSingleton {
    /**
     * 构造器私有化确保类实例唯一性
     */
    private StaticHolderSingleton() {
    }

    /**
     * 类初始化时被创建,静态变量初次访问时JVM回对其进行初始化，且仅初始化1次
     */
    private static class InnerClass {
        final static StaticHolderSingleton INSTANCE = new StaticHolderSingleton();
    }

    public static StaticHolderSingleton getInstance() {
        return InnerClass.INSTANCE;
    }

    public static void main(String[] args) {
        StaticHolderSingleton instance = StaticHolderSingleton.getInstance();
        log.info(JSON.toJSONString(instance));
    }
}
