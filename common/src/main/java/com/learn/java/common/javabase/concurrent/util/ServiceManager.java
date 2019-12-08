package com.learn.java.common.javabase.concurrent.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class ServiceManager {

    static volatile CountDownLatch latch = new CountDownLatch(3);

    static Set<Service> services;

    static Set<Service> getServices() {
        services = new HashSet<>();
        services.add(new SampleServiceA(latch));
        services.add(new SampleServiceB(latch));
        services.add(new SampleServiceC(latch));
        return services;
    }

    public static void startServices() {
        for (Service service : services) {
            service.start();
        }
    }

    public static boolean checkServiceStatus() {
        boolean allIsOk = true;
        try {
            //服务全部启动前等待
            System.out.println(Thread.currentThread().getName() + ",is waiting");
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        for (Service service : services) {
            if (!service.isStarted()) {
                allIsOk = false;
                break;
            }
        }
        return allIsOk;
    }

    public static void main(String[] args) {
        getServices();
        startServices();
        boolean allIsOk = checkServiceStatus();
        if (allIsOk) {
            System.out.println("service start success");
        } else {
            System.err.println("service start failure");
            System.exit(1);
        }
    }

}
