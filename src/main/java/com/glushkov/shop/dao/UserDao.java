package com.glushkov.shop.dao;

import com.glushkov.shop.entity.User;

public interface UserDao {

    User findUserByLogin(String login);

    void save(User user);

    boolean isLoginExist(String login);
}
