package com.glushkov.shop.service.impl;

import com.glushkov.shop.dao.UserDao;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.service.UserService;

public class DefaultUserService implements UserService {

    private final UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public boolean isLoginExist(String login) {
        return userDao.isLoginExist(login);
    }
}
