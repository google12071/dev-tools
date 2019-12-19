package com.learn.event.handler;

import com.google.common.eventbus.Subscribe;
import com.ssm.config.eventBus.Event;

public class EventHandlerProxy implements IEventHandler {

    private IEventHandler handler;

    public EventHandlerProxy(IEventHandler handler) {
        this.handler = handler;
    }

    @Subscribe
    @Override
    public void handle(Event event) {
        handler.handle(event);
    }
}
