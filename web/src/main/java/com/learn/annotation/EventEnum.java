package com.learn.annotation;

public enum EventEnum {
    SAVE_USER_INFO("SAVE_USER_INFO", "保存用户信息");

    /**
     * 操作
     */
    private String operation;
    /**
     * 描述
     */
    private String desc;

    EventEnum(String operation, String desc) {
        this.operation = operation;
        this.desc = desc;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
