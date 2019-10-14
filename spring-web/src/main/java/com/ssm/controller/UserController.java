package com.ssm.controller;

import com.ssm.model.User;
import com.ssm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
}
