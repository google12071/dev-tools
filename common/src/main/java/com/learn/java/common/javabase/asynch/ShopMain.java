package com.learn.java.common.javabase.asynch;

import com.learn.java.common.pojo.Shop;

public class ShopMain {

    public static void main(String[] args) {
        try {
            Shop shop = new Shop("BestShop");

//            long start = System.currentTimeMillis();
//            Future<Double> future = shop.getPriceAsync("my favorite product");
//            System.out.println("future cost:" + (System.currentTimeMillis() - start) + "ms");
//            System.out.println("value:" + future.get() + ",value cost:" + (System.currentTimeMillis() - start) + "ms");


            Double value = shop.getPriceBasic("BestShop");
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
