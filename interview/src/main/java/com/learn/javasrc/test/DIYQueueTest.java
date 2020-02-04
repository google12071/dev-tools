package com.learn.javasrc.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义队列测试
 */
@Slf4j
public class DIYQueueTest {
    // 我们需要测试的队列
    private final static DIYQueue<String> queue = new DIYLinkedQueue<>();

    // 生产者
    class Product implements Runnable {
        private final String message;

        public Product(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                boolean success = queue.put(message);
                if (success) {
                    log.info("put {} success", message);
                    return;
                }
                log.info("put {} fail", message);
            } catch (Exception e) {
                log.info("put {} fail", message);
            }
        }
    }

    // 消费者
    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                String message = (String) queue.take();
                log.info("consumer message :{}", message);
            } catch (Exception e) {
                log.info("consumer message fail", e);
            }
        }
    }

    // 场景测试
    @Test
    public void testDIYQueue() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        for (int i = 0; i < 1000; i++) {
            // 是偶数的话，就提交一个生产者，奇数的话提交一个消费者
            if (i % 2 == 0) {
                executor.submit(new Product(i + ""));
                continue;
            }
            executor.submit(new Consumer());
        }
        Thread.sleep(10000);
    }
}
