package com.learn.java.common.java8.collector.usage;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

/**
 * 数据聚合
 */
public class Summarizing {
    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(x -> isPrime(x)));
    }

    public static boolean isPrime(int candidate){
        return IntStream.rangeClosed(2, candidate - 1)
                       .limit((long) Math.floor(Math.sqrt((double) candidate)) - 1)
                       .noneMatch(i -> candidate % i == 0);
    }

    public static void main(String[] args) {
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(100));
    }

}
