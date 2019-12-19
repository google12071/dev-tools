package com.learn.event.handler;

import com.ssm.config.eventBus.Event;

public interface IEventHandler {
    void handle(Event event);
}
