package com.learn.java.common.jvm.gc;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName CanReliveObj
 * @Description: gc后，调用finalize()方法,可能导致对象复活
 *  finalize()函数是一个糟糕的设计，其一：finalize可能造成引用外泄，无意中复活对象；其二：finalize由系统调用，切调用时间不明确，不推荐使用
 *
 * @Author lfq
 * @Date 2020/2/7
 **/
@Slf4j
public class CanReliveObj {
    private static CanReliveObj obj;

    @Override
    public String toString() {
        return "I am CanReliveObj";
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("CanReliveObj finalize called");
        //finalize方法中复活
        obj = this;
    }

    /**
     * 校验对象状态
     */
    private static void checkObj() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (obj == null) {
            System.out.println("obj为空");
        } else {
            System.out.println("obj不为空:" + obj.hashCode());
        }
    }

    public static void main(String[] args) {
        obj = new CanReliveObj();
        obj = null;
        System.out.println("第1次执行GC");
        System.gc();
        //校验对象状态
        checkObj();

        obj = null;
        System.out.println("第二次执行GC");
        System.gc();
        //校验对象状态
        checkObj();
    }
}
