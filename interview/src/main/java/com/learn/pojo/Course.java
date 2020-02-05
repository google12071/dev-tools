package com.learn.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lfq
 */
@Data
public class Course implements Serializable {
    private static final long serialVersionUID = 2176650403215446464L;
    /**
     * 课程 ID
     */
    private Long id;

    /**
     * 课程 name
     */
    private String name;

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
