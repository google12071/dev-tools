package com.learn.java.common.thirdpart;

import lombok.Data;

/**
 * @ClassName OrderMessage
 * @Description:
 * @Author lfq
 * @Date 2020/2/10
 **/
@Data
public class OrderMessage {
    private String message;
    public OrderMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
