package com.learn.javasrc.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列
 *
 * @author lfq
 */
@Slf4j
public class DelayQueueTest {

    /**
     * 队列生产者
     */
    public static class Producer implements Runnable {
        private final BlockingQueue queue;

        public Producer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                log.info("begin put");
                long beginTime = System.currentTimeMillis();
                queue.put(new DelayDTO(System.currentTimeMillis() + 2000L, beginTime));//延迟 2 秒执行
                queue.put(new DelayDTO(System.currentTimeMillis() + 5000L, beginTime));//延迟 5 秒执行
                queue.put(new DelayDTO(System.currentTimeMillis() + 1000L * 10, beginTime));//延迟 10 秒执行
                log.info("end put");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 队列消费者
     */
    public static class Consumer implements Runnable {
        private final BlockingQueue queue;

        public Consumer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                log.info("Consumer begin");
                ((DelayDTO) queue.take()).run();
                ((DelayDTO) queue.take()).run();
                ((DelayDTO) queue.take()).run();
                log.info("Consumer end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 延迟队列实体
     */
    @Data
    public static class DelayDTO implements Delayed {
        private Long s;
        private Long startTime;

        public DelayDTO(Long s, Long startTime) {
            this.s = s;
            this.startTime = startTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(s - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        public void run() {
            log.info("现在已经过了{}秒钟", (System.currentTimeMillis() - startTime) / 1000);
        }
    }

    public static void main(String[] args) {
        BlockingQueue q = new DelayQueue();
        DelayQueueTest.Producer p = new DelayQueueTest.Producer(q);
        DelayQueueTest.Consumer c = new DelayQueueTest.Consumer(q);
        new Thread(c).start();
        new Thread(p).start();
    }
}
