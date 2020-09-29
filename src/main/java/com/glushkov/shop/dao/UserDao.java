package com.glushkov.shop.dao;

import com.glushkov.shop.entity.User;

public interface UserDao {

    User findUser(String login, String password);
}
