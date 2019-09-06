package com.learn.java.common.pojo;

import java.io.Serializable;


public class  User implements Serializable {
    private static final long serialVersionUID = 1328162770555321399L;

    private Long id;

    private String name;

    private String address;

    private Boolean member;

    private Integer sex;

    public User(Long id, String name, String address, Boolean member, Integer sex) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.member = member;
        this.sex = sex;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getMember() {
        return member;
    }

    public void setMember(Boolean member) {
        this.member = member;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", address='" + address + '\'' +
                        ", member=" + member +
                        ", sex=" + sex +
                        '}';
    }
}
