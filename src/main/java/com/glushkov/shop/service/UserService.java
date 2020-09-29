package com.glushkov.shop.service;

import com.glushkov.shop.dao.UserDao;
import com.glushkov.shop.entity.User;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUser(String login, String password){
        return userDao.findUser(login,password);
    }
}
