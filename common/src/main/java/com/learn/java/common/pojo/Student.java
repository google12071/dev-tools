package com.learn.java.common.pojo;

public class Student extends Person{
    private int score;
    private String desc;

    public void introduce(float height) {
        System.out.println("name:" + name + ",height:" + height);
    }

    public void introduce() {
        System.out.println("name:" + name);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Student{" +
                "score=" + score +
                ", desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
