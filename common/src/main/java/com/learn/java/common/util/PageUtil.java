package com.learn.java.common.util;

import com.learn.java.common.pojo.Shop;
import com.learn.java.common.pojo.User;

import java.util.Arrays;
import java.util.List;

/**
 * 分页
 */
public class PageUtil {

    private static List<User> userList = Arrays.asList(
            new User(1L, "张三", "北京市", true, 1),
            new User(2L, "李四", "北京市", true, 1),
            new User(3L, "王五", "北京市", true, 1),
            new User(4L, "马六", "北京市", true, 1),
            new User(5L, "谢七", "北京市", true, 1)
    );

    private static List<Shop> shopList = Arrays.asList(new Shop("大润发"), new Shop("苏宁易购"));


    public static <T> List<T> getDataByPage(List<T> list, int start, int count) {
        List<T> subList;
        int size = list.size();
        if (start + count <= size) {
            subList = list.subList(start, start + count);
        } else {
            subList = list.subList(start, size);
        }
        return subList;
    }

    public static void main(String[] args) {
        List<User> result = getDataByPage(userList, 0, 10);

        List<Shop> shops = getDataByPage(shopList, 0, 20);

        System.out.println(result);
        System.out.println(shops);
    }
}
