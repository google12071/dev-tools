package com.learn.java.common.collection;

/**
 * 颜色枚举类
 */
public enum Color {
    RED(1, "红色"), BLACK(2, "黑色"), WHITE(3, "白色"), YELLOW(4, "黄色"), BLUE(5, "绿色");

    /**
     * 颜色编码
     */
    private Integer code;

    /***
     * 颜色描述
     */
    private String desc;

    Color(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
