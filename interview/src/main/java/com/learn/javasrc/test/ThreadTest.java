package com.learn.javasrc.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * 线程测试类
 *
 * @author lfq
 */
@Slf4j
public class ThreadTest {

    @Test
    public void interrupt() throws InterruptedException {
        log.info(Thread.currentThread().getName() + ",running...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info(Thread.currentThread() + ",running...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    log.info(Thread.currentThread() + ",interrupted...");
                    e.printStackTrace();
                }
                log.info(Thread.currentThread() + ",finish...");
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }

    @Test
    public void join() throws Exception {
        Thread main = Thread.currentThread();
        log.info("{} is run。", main.getName());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("{} begin run", Thread.currentThread().getName());
                try {
                    Thread.sleep(30000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{} end run", Thread.currentThread().getName());
            }
        });
        // 开一个子线程去执行
        thread.start();
        // 当前主线程等待子线程执行完成之后再执行
        thread.join();
        log.info("{} is end", Thread.currentThread());
    }

    @Test
    public void buildThreadPool() throws ExecutionException, InterruptedException {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService service = new ThreadPoolExecutor(10, 15, 5,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), factory);

        FutureTask futureTask1 = new FutureTask((Callable<String>) () -> {
            Thread.sleep(30000);
            // 返回一句话
            return "我是子线程" + Thread.currentThread().getName();
        });
        service.submit(futureTask1);
        String result = (String) futureTask1.get();
        log.info("result is:" + result);

        FutureTask futureTask2 = new FutureTask(new Runnable() {
            @Override
            public void run() {
                log.info(Thread.currentThread().getName() + ",is running...");
            }
        }, null);
        service.submit(futureTask2);
    }

    @Test
    public void testThreadByCallable() throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask((Callable<String>) () -> {
            Thread.sleep(3000);
            String result = "我是子线程" + Thread.currentThread().getName();
            log.info("子线程正在运行：{}", Thread.currentThread().getName());
            return result;
        });
        new Thread(futureTask).start();
        log.info("返回的结果是 {}", futureTask.get());
        log.info("取消的结果是 {}，执行的结果是{}", futureTask.isCancelled(), futureTask.isDone());

    }

    @Test
    public void testJoin2() throws Exception {
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("我是子线程 2,开始沉睡");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("我是子线程 2，执行完成");
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("我是子线程 1，开始运行");
                try {
                    log.info("我是子线程 1，我在等待子线程 2");
                    // 这里是代码关键
                    thread2.join();

                    log.info("我是子线程 1，子线程 2 执行完成，我继续执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("我是子线程 1，执行完成");
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
    }

    /**
     * 测试子线程从父线程继承ThreadLocal数据
     */
    @Test
    public void testThreadLocal() {
        ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set(10);
        log.info(Thread.currentThread().getName() + ",value:" + threadLocal.get());

        Thread child = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(3);
                int value = threadLocal.get();
                log.info(Thread.currentThread().getName() + ",value:" + value);
            }
        });
        child.start();
        log.info(Thread.currentThread().getName() + ",value:" + threadLocal.get());

    }
}
