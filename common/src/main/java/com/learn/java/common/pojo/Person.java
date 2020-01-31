package com.learn.java.common.pojo;

public class Person {
    public String name;
    /**
     * 确保子类可见
     */
    public int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
