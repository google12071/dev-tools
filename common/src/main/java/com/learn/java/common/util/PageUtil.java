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


    public static <T, E extends PageBase> E getDataByPage(E element, List<T> list, int start, int count) {
        List<T> subList;
        int size = list.size();
        if (start + count <= size) {
            subList = list.subList(start, start + count);
            element.setHasMore(true);
            element.setStartIndex(start + count);
        } else {
            subList = list.subList(start, size);
            element.setHasMore(false);
            element.setStartIndex(size);
        }
        element.setDataList(subList);
        return element;
    }

    public static void main(String[] args) {
        UserRes userRes = new UserRes();
        ShopRes shopRes = new ShopRes();

        userRes = getDataByPage(userRes, userList, 0, 10);
        userRes.setAge(3);

        shopRes = getDataByPage(shopRes, shopList, 0, 1);
        shopRes.setLocation("新河街");

        System.out.println(userRes);
        System.out.println(shopRes);
    }
}
