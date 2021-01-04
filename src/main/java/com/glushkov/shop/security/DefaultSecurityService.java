package com.glushkov.shop.security;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.service.UserService;
import com.glushkov.shop.util.PropertyReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultSecurityService implements SecurityService, Runnable {
    private List<Session> sessionList = new CopyOnWriteArrayList<>();
    private PropertyReader propertyReader = ServiceLocator.getPropertyReader();
    private UserService userService;

    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Session login(String login, String password) {
        User user = userService.findUserByLogin(login);
        if (user != null) {
            String sole = user.getSole();//TODO salt
            String hashPassword = DigestUtils.sha256Hex(sole.concat(password));
            long sessionMaxAge = Long.parseLong(propertyReader.getProperties().getProperty("session.max-age"));

            if (user.getPassword().equals(hashPassword)) {
                Session session = Session.builder()
                        .user(user)
                        .token(UUID.randomUUID().toString())
                        .expireDate(LocalDateTime.now().plusSeconds(sessionMaxAge))
                        .build();
                sessionList.add(session);
                return session;
            }
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
                if (session.getExpireDate().isBefore(LocalDateTime.now())) {
                    sessionList.remove(session);
                    return null;
                }
                return session;
            }
        }
        return null;
    }

    @Override
    public void run() {
        sessionList.removeIf(session -> session.getExpireDate().isBefore(LocalDateTime.now()));
    }

    void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }
}

