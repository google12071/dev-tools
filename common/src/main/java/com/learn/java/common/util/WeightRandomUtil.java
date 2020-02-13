package com.learn.java.common.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Pair;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @ClassName WeightRandomUtil
 * @Description:权重抽奖工具类
 * @Author lfq
 * @Date 2020/2/13
 **/
@Slf4j
public class WeightRandomUtil<K, V extends Number> {
    private TreeMap<Double, K> weightMap = new TreeMap<>();

    public WeightRandomUtil(List<Pair<K, V>> list) {
        Preconditions.checkNotNull(list, "list can NOT be null!");
        for (Pair<K, V> pair : list) {
            //统一转为double
            double lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey();
            //权重累加
            this.weightMap.put(pair.getValue().doubleValue() + lastWeight, pair.getKey());
        }
    }

    /**
     * 利用TreeMap.tailMap().firstKey()即可找到目标元素。
     * @return
     */
    public K random() {
        double randomWeight = this.weightMap.lastKey() * Math.random();
        SortedMap<Double, K> tailMap = this.weightMap.tailMap(randomWeight, false);
        return this.weightMap.get(tailMap.firstKey());
    }

    /**
     * 模拟十亿次抽取
     * @param args
     */
    public static void main(String[] args) {
        List<Pair<String, Integer>> pairList = Lists.newArrayList(new Pair("A", 1), new Pair("B", 2), new Pair("C", 12));
        int a = 0;
        int b = 0;
        int c = 0;
        int total = 100000000;
        int sum = 15;
        WeightRandomUtil randomUtil = new WeightRandomUtil(pairList);
        for (int i = 0; i < total; i++) {
            String value = (String) randomUtil.random();
            if ("A".equals(value)) {
                a++;
            } else if ("B".equals(value)) {
                b++;
            } else if ("C".equals(value)) {
                c++;
            }
        }
        double rateA = (double) a / total;
        double rate1 = (double) 1 / sum;
        double rateB = (double) b / total;
        double rate2 = (double) 2 / sum;
        double rateC = (double) c / total;
        double rate3 = (double) 15 / sum;
        log.info("rateA:{},rateB:{},rateC:{}", rateA, rateB, rateC);
        log.info("rate1:{},rate2:{},rate3:{}", rate1, rate2, rate3);
    }
}
