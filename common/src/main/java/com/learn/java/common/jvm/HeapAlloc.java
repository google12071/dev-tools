package com.learn.java.common.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName HeapAlloc
 * @Description: 堆内存分配
 * @Author lfq
 * @Date 2020/2/6
 **/
@Slf4j
public class HeapAlloc {
    public static void main(String[] args) {
        System.out.println("max=" + Runtime.getRuntime().maxMemory() + " bytes");
        System.out.println("free=" + Runtime.getRuntime().freeMemory() + " bytes");
        System.out.println("total=" + Runtime.getRuntime().totalMemory() + " bytes");
        byte[] b = new byte[1 * 1024 * 1024];
        System.out.println("=========================================================");
        System.out.println("max=" + Runtime.getRuntime().maxMemory() + " bytes");
        System.out.println("free=" + Runtime.getRuntime().freeMemory() + " bytes");
        System.out.println("total=" + Runtime.getRuntime().totalMemory() + " bytes");
        b = new byte[4 * 1024 * 1024];
        System.out.println("=========================================================");
        System.out.println("max=" + Runtime.getRuntime().maxMemory() + " bytes");
        System.out.println("free=" + Runtime.getRuntime().freeMemory() + " bytes");
        System.out.println("total=" + Runtime.getRuntime().totalMemory() + " bytes");
    }
}
