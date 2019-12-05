package com.learn.java.common.collection;

import com.google.common.collect.Lists;

import java.util.EnumMap;
import java.util.List;

public class EnumMapTest {
    public static void main(String[] args) {
        List<Color> colorList = Lists.newArrayList(Color.YELLOW, Color.BLUE, Color.BLACK,
                Color.RED, Color.YELLOW, Color.WHITE, Color.YELLOW, Color.BLUE);

        EnumMap<Color, Integer> colorMap = new EnumMap<>(Color.class);
        colorList.forEach(x -> {
            Integer count = colorMap.get(x);
            if (colorMap.get(x) == null) {
                count = 1;
            } else {
                count++;
            }
            colorMap.put(x, count);
        });

        System.out.println(colorMap);
    }
}
