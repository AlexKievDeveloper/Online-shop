package com.glushkov.shop.security;

public interface SecurityService {
    Session login(String login, String password);

    void logout(String token);

    Session getSession(String token);
}
