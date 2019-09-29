package com.learn.java.common.pojo;

import com.learn.java.common.javabase.asynch.Util;

import java.util.Random;
import java.util.concurrent.*;

public class Shop {
    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getName() {
        return name;
    }

    public Random getRandom() {
        return random;
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        long start = System.currentTimeMillis();
        Util.delay(5000);
        double value = random.nextDouble() * product.charAt(0) + product.charAt(1);
        long end = System.currentTimeMillis();
        System.out.println("calculatePrice start:" + start + ",end:" + end + ",cost:" + (end - start) + "ms");
        return value;
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePrice(product);
            futurePrice.complete(price);
        }).start();

        return futurePrice;
    }


    /**
     * Future用法
     * @param product
     * @return
     */
    public Double getPriceBasic(String product) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return calculatePrice(product);
            }
        });

        doSomethingElse();

        try {
            //future结果返回前，阻塞当前线程
            return future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void doSomethingElse() {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        System.out.println("doSomethingElse start:" + start + ",end:" + end + ",cost:" + (end - start) + "ms");
    }

}
