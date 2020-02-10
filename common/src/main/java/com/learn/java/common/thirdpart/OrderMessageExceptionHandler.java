package com.learn.java.common.thirdpart;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName OrderMessageExceptionHandler
 * @Description:
 * @Author lfq
 * @Date 2020/2/10
 **/
@Slf4j
public class OrderMessageExceptionHandler implements SubscriberExceptionHandler {
    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        exception = new Exception(exception.getMessage());
        log.info("error:" + exception.getMessage());
        log.info("context,event:{},", context.getEvent());
    }
}
