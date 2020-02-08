package com.learn.java.common.jvm.gc;

/**
 * @ClassName AllocateEden
 * @Description:新创建对象进入伊甸区
 * @Author lfq
 * @Date 2020/2/8
 **/
public class AllocateEden {
    public static void main(String[] args) {
        for (int i = 0; i < 5 * 1024; i++) {
            byte[] b = new byte[1024];
        }
    }
}
