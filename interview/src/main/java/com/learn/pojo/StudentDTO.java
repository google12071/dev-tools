package com.learn.pojo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lfq
 */
@Data
@Slf4j
public class StudentDTO implements Serializable {
    private static final long serialVersionUID = -3725753663568084401L;

    /**
     * 初始化数据
     */
    private final List<StudentDTO> studentDTOList = new ArrayList<StudentDTO>() {
        {
            // 添加学生数据1
            add(new StudentDTO(1L, "W199", "小美", "WM", 100D, new ArrayList<Course>() {
                {
                    // 添加学生学习的课程
                    add(new Course(300L, "语文"));
                    add(new Course(301L, "数学"));
                    add(new Course(302L, "英语"));
                }
            }));

            //添加学生数据2
            add(new StudentDTO(2L, "W25", "小美", "WM", 100D, Lists.newArrayList()));

            //添加学生数据3
            add(new StudentDTO(3L, "W3", "小名", "M", 90D, new ArrayList<Course>() {
                {
                    add(new Course(300L, "语文"));
                    add(new Course(304L, "体育"));
                }
            }));

            //添加学生数据4
            add(new StudentDTO(4L, "W1", "小蓝", "M", 10D, new ArrayList<Course>() {
                {
                    add(new Course(301L, "数学"));
                    add(new Course(305L, "美术"));
                }
            }));
        }
    };

    /**
     * 构造器
     *
     * @param id
     * @param code
     * @param name
     * @param sex
     * @param scope
     * @param courseList
     */
    public StudentDTO(Long id, String code, String name, String sex, Double scope, List<Course> courseList) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.sex = sex;
        this.scope = scope;
        this.courseList = courseList;
    }

    /**
     * id
     */
    private Long id;
    /**
     * 学号 唯一标识
     */
    private String code;
    /**
     * 学生名字
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 分数
     */
    private Double scope;

    /**
     * 要学习的课程
     */
    private List<Course> courseList;
}
