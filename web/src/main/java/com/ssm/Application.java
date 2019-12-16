package com.ssm;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * SpringBoot web项目启动类
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.ssm.dao"})
@EntityScan(basePackages = {"com.ssm.model"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
