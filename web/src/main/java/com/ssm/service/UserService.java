package com.ssm.service;

import com.ssm.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    void addUser(User user);
}
