package com.glushkov.shop.service;

import com.glushkov.shop.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final Cookie[] cookies;

    public AuthenticationServiceTest() {
        cookies = new Cookie[1];
        Cookie cookie = new Cookie("user-token", "a2102");
        cookies[0] = cookie;
        AuthenticationService.getTokensRoleMap().put("a2102", Role.ADMIN);
    }

    @AfterEach
    void afterAll() {
        AuthenticationService.getTokensRoleMap().remove("a2102");
    }

    @Test
    @DisplayName("Return true if user role is admin")
    void isAdmin() {
        //when
        boolean actual = authenticationService.isAdmin(cookies);
        //then
        assertTrue(actual);
    }

    @Test
    @DisplayName("Return true if user role is admin or user")
    void isUserOrAdmin() {
        //when
        boolean actual = authenticationService.isUserOrAdmin(cookies);
        //then
        assertTrue(actual);
    }

    @Test
    @DisplayName("Returns a cookie with role admin or user")
    void getValidCookie() {
        //when
        Cookie actual = authenticationService.getValidCookie(cookies);
        //then
        assertNotNull(actual);
        assertEquals("a2102", actual.getValue());
        assertEquals("user-token", actual.getName());
    }
}