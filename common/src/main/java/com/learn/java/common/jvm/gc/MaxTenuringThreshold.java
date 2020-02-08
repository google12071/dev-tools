package com.learn.java.common.jvm.gc;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName MaxTenuringThreshold
 * @Description:老年对象进入老年代
 * @Author lfq
 * @Date 2020/2/8
 **/
@Slf4j
public class MaxTenuringThreshold {
    public static final int _1M = 1024 * 1024;
    public static final int _1k = 1024;

    public static void main(String[] args) {
        Map<Integer, byte[]> map = Maps.newHashMap();
        for (int i = 0; i < 5 * _1k; i++) {
            byte[] b = new byte[_1k];
            map.put(i, b);
        }

        for (int k = 0; k < 17; k++) {
            for (int i = 0; i < 270; i++) {
                byte[] g = new byte[_1M];
                System.out.println(g);
            }
        }
    }
}
