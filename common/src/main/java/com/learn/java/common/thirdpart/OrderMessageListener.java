package com.learn.java.common.thirdpart;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName OrerMessageListener
 * @Description:
 * @Author lfq
 * @Date 2020/2/10
 **/
@Slf4j
public class OrderMessageListener {
    @Subscribe
    public void dealWithEvent(OrderMessage orderMessage) {
        log.info(Thread.currentThread().getName() + ",receive message:{}", orderMessage.getMessage());
        throw new RuntimeException("orderMessage error");
    }
}
