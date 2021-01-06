package com.glushkov.shop.security;

import com.glushkov.shop.entity.User;
import com.glushkov.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSecurityService implements SecurityService, Runnable {
    private List<Session> sessionList = new CopyOnWriteArrayList<>();
    private final UserService userService;
    @Value("${session.max-age}")
    private long sessionMaxAge;

    @Override
    public Session login(String login, String password) {
        log.info("Post request for login");
        User user = userService.findUserByLogin(login);
        if (user != null) {
            log.info("User is not null");
            String salt = user.getSole();
            String hashPassword = DigestUtils.sha256Hex(salt.concat(password));

            if (user.getPassword().equals(hashPassword)) {
                Session session = Session.builder()
                        .user(user)
                        .token(UUID.randomUUID().toString())
                        .expireDate(LocalDateTime.now().plusSeconds(sessionMaxAge))
                        .build();
                sessionList.add(session);
                log.info("Password is correct");
                log.info("Session: {}", session);
                return session;
            }
        }
        log.info("Login or password is not correct");
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

