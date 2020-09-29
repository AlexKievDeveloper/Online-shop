package com.glushkov.shop.service.impl;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DefaultAuthenticationService implements AuthenticationService {
    private static final Map<String, Role> TOKENS_ROLE_MAP = new ConcurrentHashMap<>();

    public boolean isAdmin(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (TOKENS_ROLE_MAP.get(cookie.getValue()).equals(Role.ADMIN)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isUserOrAdmin(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (TOKENS_ROLE_MAP.get(cookie.getValue()).equals(Role.USER) ||
                            TOKENS_ROLE_MAP.get(cookie.getValue()).equals(Role.ADMIN)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Cookie getValidCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (DefaultAuthenticationService.getTokensRoleMap().get(cookie.getValue()).equals(Role.USER) ||
                            DefaultAuthenticationService.getTokensRoleMap().get(cookie.getValue()).equals(Role.ADMIN)) {
                        return cookie;
                    }
                }
            }
        }
        log.error("Error while getting valid cookie");
        return null;
    }

    public static Map<String, Role> getTokensRoleMap() {
        return TOKENS_ROLE_MAP;
    }
}
