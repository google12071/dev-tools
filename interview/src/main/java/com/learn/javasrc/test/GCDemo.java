package com.learn.javasrc.test;

/**
 * @ClassName GCDemo
 * @Description:GC日志
 * @Author lfq
 * @Date 2020/2/6
 **/
public class GCDemo {
    public static void main(String[] args) {
        byte[] b = new byte[4 * 1024 * 1024];
        System.out.println("first allocate");
        b = new byte[4 * 1024 * 1024];
        System.out.println("second allocate");
    }
}
