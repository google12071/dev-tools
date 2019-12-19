package com.ssm.config.eventBus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.learn.annotation.EventSubscribe;
import com.learn.event.handler.EventHandlerProxy;
import com.learn.event.handler.IEventHandler;
import com.learn.event.handler.ThreadSafeEventHandlerProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class EventPostProcessor implements BeanPostProcessor {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private AsyncEventBus asyncEventBus;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        if (ArrayUtils.isEmpty(methods)) {
            return bean;
        }
        for (Method method : methods) {
            EventSubscribe subscribe = method.getAnnotation(EventSubscribe.class);
            if (subscribe == null) {
                continue;
            }
            IEventHandler eventHandlerProxy;
            if (subscribe.threadSafe()) {
                eventHandlerProxy = new ThreadSafeEventHandlerProxy((IEventHandler) bean);
            } else {
                eventHandlerProxy = new EventHandlerProxy((IEventHandler) bean);
            }

            eventBus.register(eventHandlerProxy);
            asyncEventBus.register(eventHandlerProxy);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
