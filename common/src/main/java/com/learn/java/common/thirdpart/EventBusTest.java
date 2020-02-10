package com.learn.java.common.thirdpart;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

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

    public static void main(String[] args) {
        EventBus eventBus = new EventBus(new OrderMessageExceptionHandler());
        eventBus.register(new OrderMessageListener());
        eventBus.post(new OrderMessage("hello"));
    }
}
