package com.learn.javasrc.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName TargetClass
 * @Description:类加载的目标类
 * @Author lfq
 * @Date 2020/2/6
 **/
@Slf4j
public class A {
    /**
     * 简单求和方法，由自定义类加载器加载后，根据反射调用
     *
     * @param a 参数a
     * @param b 参数b
     * @return sum
     */
    public int sum(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int sum = new A().sum(1, 2);
        log.info("sum is:{}", sum);
    }
}
