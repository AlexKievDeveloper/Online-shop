package com.glushkov.shop.service;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.service.impl.DefaultAuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    private final DefaultAuthenticationService defaultAuthenticationService = new DefaultAuthenticationService();
    private final Cookie[] cookies;

    public AuthenticationServiceTest() {
        cookies = new Cookie[]{new Cookie("user-token", "a2102")};
        DefaultAuthenticationService.getTokensRoleMap().put("a2102", Role.ADMIN);
    }

    @AfterEach
    void afterAll() {
        DefaultAuthenticationService.getTokensRoleMap().remove("a2102");
    }

    @Test
    @DisplayName("Return true if user role is admin")
    void isAdmin() {
        //when
        boolean actual = defaultAuthenticationService.isAdmin(cookies);
        //then
        assertTrue(actual);
    }

    @Test
    @DisplayName("Return true if user role is admin or user")
    void isUserOrAdmin() {
        //when
        boolean actual = defaultAuthenticationService.isUserOrAdmin(cookies);
        //then
        assertTrue(actual);
    }

    @Test
    @DisplayName("Returns a cookie with role admin or user")
    void getValidCookie() {
        //when
        Cookie actual = defaultAuthenticationService.getValidCookie(cookies);
        //then
        assertNotNull(actual);
        assertEquals("a2102", actual.getValue());
        assertEquals("user-token", actual.getName());
    }
}