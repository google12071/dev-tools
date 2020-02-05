package com.learn.javasrc.generic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Generic
 * @Description: 泛型类
 * @Author lfq
 * @Date 2020/2/5
 * @Version 1.0
 **/
@Data
@Slf4j
public class Generic<T> {
    private T data;

    public Generic(T data) {
        this.data = data;
    }
}
