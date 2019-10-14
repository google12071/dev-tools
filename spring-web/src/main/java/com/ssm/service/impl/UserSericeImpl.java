package com.ssm.service.impl;

import com.ssm.dao.UserDAO;
import com.ssm.model.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSericeImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> getAllUser() {
        return null;
    }
}
