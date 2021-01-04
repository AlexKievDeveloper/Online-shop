package com.glushkov.shop.service;

import com.glushkov.shop.entity.User;

public interface UserService {

    User findUserByLogin(String login);

    void save(User user);

    boolean isLoginExist(String login);
}
