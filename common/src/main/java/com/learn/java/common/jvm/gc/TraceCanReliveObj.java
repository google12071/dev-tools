package com.learn.java.common.jvm.gc;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @ClassName TraceCanReliveObj
 * @Description: 虚引用是所有引用类型中最弱对一个，随时有可能被GC回收，当垃圾回收器准备回收对象时，若发现对象有虚引用
 * 则将这个虚引用加入引用队列，使用虚引用用于跟踪复活对象当回收
 * @Author lfq
 * @Date 2020/2/7
 **/
@Slf4j
public class TraceCanReliveObj {
    /**
     * 类实例对象
     */
    public static TraceCanReliveObj obj;
    /**
     * 虚引用队列
     */
    public static ReferenceQueue<TraceCanReliveObj> phantomQueue = null;

    /**
     * 虚引用队列检测线程
     */
    public static class CheckRefQueue extends Thread {
        @Override
        public void run() {
            while (true) {
                if (phantomQueue != null) {
                    PhantomReference<TraceCanReliveObj> objt = null;
                    try {
                        log.info(Thread.currentThread().getName() + "," + "running");
                        objt = (PhantomReference<TraceCanReliveObj>) phantomQueue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (objt != null) {
                        System.out.println("TraceCanReliveObj is delete by GC");
                    }
                }
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("CanReliveObj finalize called");
        //可能导致对象复活
        obj = this;
    }

    @Override
    public String toString() {
        return "I am CanReliveObj";
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t = new CheckRefQueue();
        t.setDaemon(true);
        t.start();

        phantomQueue = new ReferenceQueue<TraceCanReliveObj>();
        obj = new TraceCanReliveObj();
        PhantomReference<TraceCanReliveObj> phantomRef = new PhantomReference<TraceCanReliveObj>(obj, phantomQueue);

        obj = null;
        System.gc();
        Thread.sleep(1000);
        if (obj == null) {
            System.out.println("obj 是 null");
        } else {
            System.out.println("obj 可用");
        }
        System.out.println("第二次gc");
        obj = null;
        System.gc();
        Thread.sleep(1000);
        if (obj == null) {
            System.out.println("obj 是 null");
        } else {
            System.out.println("obj 可用");
        }
    }

}
