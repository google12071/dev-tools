package com.learn.java.common.java8.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName CompletableFutureTest
 * @Description:CompletableFuture异步处理测试类
 * @Author lfq
 * @Date 2020/2/11
 **/
@Slf4j
public class CompletableFutureTest {
    @Test
    public void basic() {
        try {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    log.info(Thread.currentThread().getName() + "start");
                    Thread.sleep(1000 * 5);
                    log.info(Thread.currentThread().getName() + "finish");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 100;
            });

            while (!future.isDone()) {
                log.info("future task has not done");
            }

            int value = future.get();
            log.info("value:{}", value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建CompletableFuture对象
     */
    @Test
    public void build() throws Exception {
        CompletableFuture<String> future = CompletableFuture.completedFuture("test");
        log.info("future content:{}", future.get());

        CompletableFuture.runAsync(() -> {
            log.info(Thread.currentThread().getName() + ",is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        /**
         * 自定义线程池执行任务
         */
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("CompletableFuture-thread-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(10, 20, 60, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(100), factory, new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture future1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                log.info(Thread.currentThread().getName() + ",finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, pool);
        future1.get();
    }

    /**
     * 计算完成后处理
     */
    @Test
    public void complete() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(this::getMoreData);
        future = future.whenComplete((value, e) -> System.out.println(value)).exceptionally(e -> {
            log.error("futureComplete error", e);
            return null;
        });
        System.out.println(future.get());
    }

    /**
     * 转换
     * @throws Exception
     */
    @Test
    public void transfer()throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<String> f = future.thenApplyAsync(i -> i * 10).thenApply(i -> i.toString());
        System.out.println(f.get());
    }

    /**
     * 组合操作
     */
    @Test
    public void compose() throws Exception{
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<String> future2 = future1.thenCompose(i -> CompletableFuture.supplyAsync(() -> (i * 10) + ""));
        System.out.println(future2.get());

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "abc");
        CompletableFuture<String> future4 = future3.thenCombine(future1, (x, y) -> x + "-" + y);
        System.out.println(future4.get());
    }


    /**
     * 纯消费(执行Action)
     */
    @Test
    public void consume() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<Void> f = future.thenAccept(System.out::println);
        System.out.println(f.get());

        CompletableFuture<Void> f2 = future.thenAcceptBoth(CompletableFuture.completedFuture(10), (x, y) -> System.out.println(x * y));
        System.out.println(f2.get());

        CompletableFuture<Void> f3 = future.thenRun(() -> System.out.println("finished"));
        System.out.println(f3.get());
    }

    /**
     * Either任意计算完成则返回
     */
    @Test
    public void either(){
        Random rand = new Random();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000 + rand.nextInt(1000));
                log.info(Thread.currentThread().getName() + ",end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000 + rand.nextInt(1000));
                log.info(Thread.currentThread().getName() + ",end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        //获取执行结果
        try {
            CompletableFuture<String> f = future.applyToEither(future2, i -> i.toString());
            log.info("value:{}", f.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void any() {
        Random rand = new Random();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

        try {
            CompletableFuture<Object> f = CompletableFuture.anyOf(future1, future2);
            System.out.println(f.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private int getMoreData() {
        Random rand = new Random();
        long t = System.currentTimeMillis();
        System.out.println("begin to start compute");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
          System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t) / 1000 + " seconds");
         return rand.nextInt(1000);
    }


    public static CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

    public static void main(String[] args) throws Exception {
        final CompletableFuture<Integer> f = compute();
        class Client extends Thread {
            CompletableFuture<Integer> f;

            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }

            @Override
            public void run() {
                try {
                    System.out.println(this.getName() + ": " + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        new Client("Client1", f).start();
        new Client("Client2", f).start();
        System.out.println("waiting");
        f.complete(100);
        f.completeExceptionally(new Exception());
        System.in.read();
    }
}
