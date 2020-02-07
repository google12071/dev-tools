package com.learn.java.common.jvm.gc;

import java.lang.ref.WeakReference;

/**
 * @ClassName WeakRef
 * @Description:弱引用，系统发生GC时，只要发现弱引用，不管系统堆空间如何，都会对对象进行回收
 * @Author lfq
 * @Date 2020/2/7
 **/
public class WeakRef {
    public static void main(String[] args) {
        UserReference userReference = new UserReference(3, "test");
        WeakReference<UserReference> weakReference = new WeakReference<>(userReference);
        userReference = null;
        System.out.println("Before GC:" + weakReference.get());
        System.gc();
        System.out.println("After GC:" + weakReference.get());
    }
}
