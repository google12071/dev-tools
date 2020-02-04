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

        FutureTask futureTask = new FutureTask((Callable<String>) () -> {
            Thread.sleep(30000);
            // 返回一句话
            return "我是子线程" + Thread.currentThread().getName();
        });
        service.submit(futureTask);
        String result = (String) futureTask.get();
        log.info("result is:" + result);
    }
}
