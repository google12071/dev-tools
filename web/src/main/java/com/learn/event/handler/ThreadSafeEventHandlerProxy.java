package com.learn.event.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.ssm.config.eventBus.Event;

public class ThreadSafeEventHandlerProxy implements IEventHandler {

    private IEventHandler handler;

    public ThreadSafeEventHandlerProxy(IEventHandler handler) {
        this.handler = handler;
    }

    @Subscribe
    @AllowConcurrentEvents
    @Override
    public void handle(Event event) {
        handler.handle(event);
    }
}
