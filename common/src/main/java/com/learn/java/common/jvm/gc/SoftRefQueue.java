package com.learn.java.common.jvm.gc;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * @ClassName SoftRefQueue
 * @Description:软引用队列，当兑现可达性发生变化时，兑现会进入改队列
 * @Author lfq
 * @Date 2020/2/7
 **/
@Slf4j
public class SoftRefQueue {
    /**
     * 引用队列
     */
    private static ReferenceQueue<UserReference> referenceQueue = null;

    /**
     * 引用队列检测线程
     */
    public static class CheckRefQueue implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (referenceQueue != null) {
                    try {
                        log.info(Thread.currentThread().getName() + ",running...");
                        UserSoftReference userReference = (UserSoftReference) referenceQueue.remove();
                        log.info("userReference:{}", userReference);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class UserSoftReference extends SoftReference<UserReference> {
        private int uid;

        public UserSoftReference(UserReference referent, ReferenceQueue<? super UserReference> q) {
            super(referent, q);
        }

        @Override
        public String toString() {
            return "UserSoftReference{" +
                    "uid=" + uid +
                    '}';
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new CheckRefQueue());
        t.setDaemon(true);
        t.start();

        UserReference userReference = new UserReference(1, "李四");
        referenceQueue = new ReferenceQueue<>();

        UserSoftReference userSoftReference = new UserSoftReference(userReference, referenceQueue);
        userReference = null;
        System.out.println(userSoftReference.get());
        System.gc();

        byte[] bytes = new byte[7 * 1024 * 1024];
        System.gc();
        System.out.println(userSoftReference.get());
    }
}
