package com.glushkov.shop.service;

import com.glushkov.shop.entity.Role;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private static final Map<String, Role> TOKENS_ROLE_MAP = new HashMap<>();

    public boolean isAdmin(Cookie[] cookies) {
        boolean isAuth = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (TOKENS_ROLE_MAP.get(cookie.getValue()).equals(Role.ADMIN)) {
                        isAuth = true;
                        break;
                    }
                }
            }
        }
        return isAuth;
    }

    public boolean isUserOrAdmin(Cookie[] cookies) {
        boolean isAuth = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (TOKENS_ROLE_MAP.get(cookie.getValue()).equals(Role.USER) ||
                            TOKENS_ROLE_MAP.get(cookie.getValue()).equals(Role.ADMIN)) {
                        isAuth = true;
                        break;
                    }
                }
            }
        }
        return isAuth;
    }

    public Cookie getValidCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (AuthenticationService.getTokensRoleMap().get(cookie.getValue()).equals(Role.USER) ||
                            AuthenticationService.getTokensRoleMap().get(cookie.getValue()).equals(Role.ADMIN)) {
                        return cookie;
                    }
                }
            }
        }
        throw new RuntimeException("Error while getting valid cookie.");
    }

    public static Map<String, Role> getTokensRoleMap() {
        return TOKENS_ROLE_MAP;
    }
}
