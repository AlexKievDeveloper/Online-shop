package com.glushkov.shop.web.filter;

import com.glushkov.shop.RootApplicationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig(RootApplicationContext.class)
@ExtendWith({MockitoExtension.class})
class AdminRoleSecurityFilterTest {
    @InjectMocks
    private AdminRoleSecurityFilter adminRoleSecurityFilter;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Autowired
    private ServletContext servletContext;

    @Test
    @DisplayName("Checks the user role for Admin role")
    void doFilter() throws IOException, ServletException {
        //prepare
        Cookie[] cookies = {new Cookie("user-token", "1111")};
        when(request.getCookies()).thenReturn(cookies);
        when(request.getServletContext()).thenReturn(servletContext);
        //when
        adminRoleSecurityFilter.doFilter(request, response, filterChain);
        //then
        verify(request).getCookies();
    }

    @Test
    @DisplayName("Redirect to login page if there is no cookies")
    void doFilterIfCookiesEqualsNull() throws IOException, ServletException {
        //prepare
        when(request.getCookies()).thenReturn(null);
        when(request.getServletContext()).thenReturn(servletContext);
        //when
        adminRoleSecurityFilter.doFilter(request, response, filterChain);
        //then
        verify(request).getCookies();
        verify(response).sendRedirect("/login");
    }
}
