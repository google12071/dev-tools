package com.learn.java.common.util;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[]args){
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        System.out.println(arrayList);
        arrayList.clear();

        System.out.println(arrayList);
        arrayList.add(3);
        arrayList.add(4);
        System.out.println(arrayList);
    }
}
