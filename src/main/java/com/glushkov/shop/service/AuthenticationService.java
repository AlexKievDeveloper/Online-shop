package com.glushkov.shop.service;

import javax.servlet.http.Cookie;

public interface AuthenticationService {

    boolean isAdmin(Cookie[] cookies);

    boolean isUserOrAdmin(Cookie[] cookies);

    Cookie getValidCookie(Cookie[] cookies);
}
