package com.ssm.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -5495629883695811573L;
    private Long uid;
    private String name;
    private int age;
    private int sex;
    private String address;
    private String des;
    private Date createTime;
    private Date updateTime;

    public User(Long uid, String name) {
        this.uid = uid;
        this.name = name;
    }
}
