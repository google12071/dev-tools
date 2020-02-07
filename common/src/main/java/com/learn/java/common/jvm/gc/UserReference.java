package com.learn.java.common.jvm.gc;

import lombok.Data;

/**
 * @ClassName UserReference
 * @Description:
 * @Author lfq
 * @Date 2020/2/7
 **/
@Data
public class UserReference {
    private Integer id;
    private String name;

    public UserReference(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
