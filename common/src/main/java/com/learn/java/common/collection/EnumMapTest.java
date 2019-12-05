package com.learn.java.common.collection;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class EnumMapTest {
    private static List<Color> colorList = Lists.newArrayList(Color.YELLOW, Color.BLUE, Color.BLACK,
            Color.RED, Color.YELLOW, Color.WHITE, Color.YELLOW, Color.BLUE);

    private static Day[] days = new Day[]{Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.MONDAY};

    public static void iteratorEnumMap() {
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
    }

    public static void getOrdinal() {
        for (Day day : days) {
            System.out.println(day.ordinal());
        }
    }

    public static void getEnumElementsByClass() {
        Class<?> clazz = Day.MONDAY.getDeclaringClass();
        if (clazz.isEnum()) {
            Day[] dsz = (Day[]) clazz.getEnumConstants();
            System.out.println(Arrays.toString(dsz));
        }
    }


    public static void main(String[] args) {
        iteratorEnumMap();
        getOrdinal();
        getEnumElementsByClass();
    }
}
