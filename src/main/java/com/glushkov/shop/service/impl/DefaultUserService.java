package com.glushkov.shop.service.impl;

import com.glushkov.shop.dao.UserDao;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.service.UserService;

public class DefaultUserService implements UserService {

    private final UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUser(String login, String password){
        return userDao.findUser(login,password);
    }
}
