package com.learn.javasrc.test;

import com.alibaba.fastjson.JSON;
import com.learn.javasrc.generic.Generic;
import com.learn.javasrc.generic.GenericFruit;
import com.learn.pojo.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GenericTest
 * @Description: 泛型测试类（泛型类、泛型接口、泛型方法）
 * @Author lfq
 * @Date 2020/2/5
 * @Version 1.0
 **/
@Slf4j
public class GenericTest {
    /**
     * 泛型仅对编译器有效，运行时本质为同一种类型
     */
    @Test
    public void sameClassType() {
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        List<Double> doubleList = new ArrayList<>();
        log.info("stringList class:{},integerList class:{},doubleList class:{}",
                stringList.getClass().getName(), integerList.getClass().getName(), doubleList.getClass().getName());
    }

    /**
     * 泛型类测试
     */
    @Test
    public void genericClass() {
        Generic<String> stringGeneric = new Generic<>("hello world");
        Generic<Integer> integerGeneric = new Generic<>(123);
        Generic<Course> courseGeneric = new Generic<>(new Course(1L, "语文"));
        log.info("string:{},integer:{},course:{}", stringGeneric.getData(), integerGeneric.getData(), JSON.toJSONString(courseGeneric.getData()));

        GenericFruit.Generic<String> generic = new GenericFruit.Generic<>();
        generic.showT1("hello");
        generic.showT2(new Course(2L, "数学"));
        generic.showT3(134);
    }
}
