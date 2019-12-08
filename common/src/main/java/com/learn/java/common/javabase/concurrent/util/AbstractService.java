package com.learn.java.common.javabase.concurrent.util;

import java.util.concurrent.CountDownLatch;

/**
 * 抽象类模拟多服务启动
 */
public abstract class AbstractService implements Service {

    private boolean isStarted = false;

    private final CountDownLatch latch;

    public AbstractService(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void start() {
        new Thread(new ServiceStarter()).start();
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    // 留给子类实现的抽象方法，用于实现服务器的启动逻辑
    protected abstract void doStart() throws Exception;

    class ServiceStarter implements Runnable {
        @Override
        public void run() {
            final String serviceName = AbstractService.this.getClass().getSimpleName();
            try {
                System.out.println("Service:" + serviceName + ",starting...");
                doStart();
                isStarted = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
                System.out.println("Service:" + serviceName + ",Done");
            }
        }
    }
}
