package com.ssm.config.eventBus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class EventBusConfig {

    @Bean
    public EventBus eventBus() {
        return new EventBus((exception, context) -> log.error("同步消息总线异常:[subscribeMethod={}, event={} ]", context.getSubscriberMethod(), context.getEvent().toString(), exception));
    }

    @Bean
    public AsyncEventBus asyncEventBus() {
        return new AsyncEventBus(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1), (exception, context) -> log.error("异步消息总线异常:[subscribeMethod={}, event={} ]", context.getSubscriberMethod(), context.getEvent().toString(), exception));
    }
}
