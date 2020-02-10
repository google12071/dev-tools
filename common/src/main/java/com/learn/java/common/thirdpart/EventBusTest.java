package com.learn.java.common.thirdpart;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * @ClassName EventBusTest
 * @Description:
 * @Author lfq
 * @Date 2020/2/10
 **/
@Slf4j
public class EventBusTest {
    public static class EventBusExecutor implements Runnable {
        @Override
        public void run() {
            log.info(Thread.currentThread() + ",EventBusExecutor running...");
        }
    }

    @Test
    public void eventBus() {
        EventBus eventBus = new EventBus((exception, context) -> {
            log.error("EventBus occur error:{},subscribeMethod:{}", context.getEventBus().toString(), context.getSubscriberMethod(), exception);
        });
        eventBus.register(new OrderMessageListener());
        eventBus.post(new OrderMessage("hello"));
    }

    @Test
    public void asyEventBus() {
        EventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1), (exception, context) -> {
            log.error("asyncEventBus occur error:{},subscribeMethod:{}", context.getEventBus().toString(), context.getSubscriberMethod(), exception);
        });
        asyncEventBus.register(new OrderMessageListener());
        asyncEventBus.post(new OrderMessage("welcome"));
    }
}
