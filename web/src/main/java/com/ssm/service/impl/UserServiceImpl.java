package com.ssm.service.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.google.gson.Gson;
import com.learn.annotation.EventEnum;
import com.ssm.config.eventBus.Event;
import com.ssm.dao.UserDAO;
import com.ssm.model.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final static Gson gson = new Gson();

    @Autowired
    UserDAO userDAO;



    @Autowired
    private AsyncEventBus asyncEventBus;

    @Override
    public List<User> getAllUser() {
        return userDAO.getAllUser();
    }

    @Override
    public void addUser(User user) {
        Event event = new Event();
        event.setEventType(EventEnum.SAVE_USER_INFO);
        event.setEventInfo(user);
        asyncEventBus.post(event);
    }
}
