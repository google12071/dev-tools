package com.learn.java.common.designPattern;

/**
 * 基于枚举的单例模式
 */
public class EnumBasedSingleton {
    /**
     * 枚举类相当于单例
     */
    public enum Singleton {
        INSTANCE;

        Singleton() {
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Singleton.INSTANCE);
            }
        });

        t.start();
    }
}
