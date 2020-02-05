package com.learn.java.common.jvm;

/**
 * @ClassName StackSOF
 * @Description:
 *  设置每个线程的栈大小：-Xss256k
 *  运行时，不断创建新的线程（且每个线程持续执行），每个线程对一个一个栈，最终没有多余的空间来为新的线程分配，导致OutOfMemoryError
 * @Author lfq
 * @Date 2020/2/5
 **/
public class StackSOF {


    private static int threadNum = 0;
    public void doSomething() {
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        final StackSOF stackOOM = new StackSOF();
        try {
            while (true) {
                threadNum++;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        stackOOM.doSomething();
                    }
                });
                thread.start();
            }
        } catch (Throwable e) {
            System.out.println("目前活动线程数量：" + threadNum);
            throw e;
        }
    }
}
