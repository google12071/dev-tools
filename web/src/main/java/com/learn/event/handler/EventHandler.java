package com.learn.event.handler;

import com.learn.annotation.EventEnum;
import com.learn.annotation.EventSubscribe;
import com.ssm.config.eventBus.Event;
import com.ssm.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventHandler implements IEventHandler {

    @EventSubscribe(threadSafe = true)
    @Override
    public void handle(Event event) {
        EventEnum eventEnum = event.getEventType();
        switch (eventEnum) {
            case SAVE_USER_INFO:
                User user = (User) event.getEventInfo();
                log.info("userService saveUserInfo,user:{}", user);
            default:
                break;
        }
    }
}
