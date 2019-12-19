package com.ssm.config.eventBus;

import com.google.common.collect.Maps;
import com.learn.annotation.EventEnum;

import java.util.Map;

public class Event<T> {
    /**
     * 事件类型枚举
     */
    private EventEnum eventType;

    /**
     * 事件信息
     */
    private T eventInfo;

    /**
     * 额外信息
     */
    private Map<String, Object> extraInfo = Maps.newHashMap();


    public EventEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventEnum eventType) {
        this.eventType = eventType;
    }

    public T getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(T eventInfo) {
        this.eventInfo = eventInfo;
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }
}
