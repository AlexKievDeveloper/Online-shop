package com.glushkov.shop.web.filter;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class AdminRoleSecurityFilterTest {
    @Mock
    SecurityService securityService;
    @InjectMocks
    AdminRoleSecurityFilter adminRoleSecurityFilter;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    User user;
    @Mock
    Session session;

    @Test
    @DisplayName("Checks the user role for Admin role")
    void doFilter() throws IOException, ServletException {
        //prepare
        Cookie[] cookies = {new Cookie("user-token", "1111")};
        when(user.getRole()).thenReturn(Role.ADMIN);
        when(session.getUser()).thenReturn(user);
        when(request.getCookies()).thenReturn(cookies);
        when(securityService.getSession("1111")).thenReturn(session);
        //when
        adminRoleSecurityFilter.doFilter(request, response, filterChain);

        //then
        verify(request).getCookies();
        verify(securityService).getSession("1111");
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Redirect to login page if there is no cookies")
    void doFilterIfCookiesEqualsNull() throws IOException, ServletException {
        //prepare
        when(request.getCookies()).thenReturn(null);
        //when
        adminRoleSecurityFilter.doFilter(request, response, filterChain);
        //then
        verify(request).getCookies();
        verify(response).sendRedirect("/login");
    }
}