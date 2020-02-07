package com.learn.java.common.jvm.gc;

import java.lang.ref.SoftReference;

/**
 * @ClassName SoftRef
 * @Description: 当堆空间不足时，会清理软引用，软引用不会引起内存泄漏
 * @Author lfq
 * @Date 2020/2/7
 **/
public class SoftRef {

    public static void main(String[] args) {
        UserReference userReference = new UserReference(1, "张三");
        /**
         * 使用强引用赋值软引用
         */
        SoftReference<UserReference> softReference = new SoftReference<>(userReference);
        userReference = null;
        System.out.println("Before GC" + softReference.get());
        System.gc();
        System.out.println("After GC" + softReference.get());

        //分配较大对象，模拟堆内存紧张
        byte[] b = new byte[7 * 1024 * 1024];
        System.gc();
        System.out.println("After bidObj GC" + softReference.get());
    }
}
