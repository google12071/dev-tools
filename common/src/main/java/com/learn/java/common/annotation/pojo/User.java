package com.learn.java.common.annotation.pojo;

import com.learn.java.common.annotation.Constraint;
import com.learn.java.common.annotation.DBTable;
import com.learn.java.common.annotation.SQLInteger;
import com.learn.java.common.annotation.SQLString;

/**
 * @ClassName User
 * @Description:
 * @Author lfq
 * @Date 2020/5/7
 **/
@DBTable(name = "user_info", comment = "用户信息表")
public class User {
    @SQLInteger(constraint = @Constraint(allowNull = false, primary = true, unique = true), comment = "用户主键ID")
    private Integer uid;

    @SQLString(constraint = @Constraint(allowNull = false, unique = true), length = 1024, value = "张三", comment = "用户姓名")
    private String name;

    @SQLInteger(constraint = @Constraint(allowNull = false), comment = "用户年龄")
    private Integer age;

    @SQLString(comment = "用户地址")
    private String address;
}
