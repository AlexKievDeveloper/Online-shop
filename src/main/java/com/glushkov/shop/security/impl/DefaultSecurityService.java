package com.glushkov.shop.security.impl;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DefaultSecurityService implements SecurityService {
    private List<Session> sessionList = new ArrayList<>();
    private UserService userService = ServiceLocator.getService("userService");

    @Override
    public Session login(String login, String password) {
        String sole = userService.findUserByLogin(login).getSole();

        String shaPassword = DigestUtils.sha256Hex(sole.concat(password));
        User user = userService.findUser(login, shaPassword);

        if (user != null) {
            Session session = Session.builder()
                    .user(user)
                    .token(UUID.randomUUID().toString())
                    .expireDate(LocalDateTime.now().plusHours(1))
                    .cart(new ArrayList<>())
                    .build();
            sessionList.add(session);
            return session;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        sessionList.removeIf(session -> session.getToken().equals(token));
    }

    @Override
    public Session getSession(String token) {
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                return session;
            }
        }
        return null;
    }
}
