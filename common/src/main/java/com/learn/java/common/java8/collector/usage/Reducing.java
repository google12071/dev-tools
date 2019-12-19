package com.learn.java.common.java8.collector.usage;

import com.learn.java.common.java8.collector.pojo.Dish;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.reducing;

public class Reducing {

    public static final List<Dish> menu = asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 400, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    public static void main(String... args) {
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodReference());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithoutCollectors());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
    }

    private static int calculateTotalCalories() {
        return menu.stream().collect(reducing(0, Dish::getCalories, (Integer a, Integer b) -> a + b));
    }

    private static int calculateTotalCaloriesWithMethodReference() {
        return menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
    }

    private static int calculateTotalCaloriesWithoutCollectors() {
        return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

    private static int calculateTotalCaloriesUsingSum() {
        return menu.stream().mapToInt(Dish::getCalories).sum();
    }
}
