package com.ssm.dao;

import com.alibaba.fastjson.JSON;
import com.ssm.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private User user;

    @Test
    public void getAllUser() {
        List<User> userList = userDAO.getAllUser();
        log.info("userList:{}", JSON.toJSONString(userList));
    }

    @Test
    public void getUser() {
        System.out.println(user);
    }
}
