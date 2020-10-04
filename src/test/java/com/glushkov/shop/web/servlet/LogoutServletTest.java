package com.glushkov.shop.web.servlet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {
    @InjectMocks
    private LogoutServlet logoutServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("Removing session from sessions list")
    void doGetTest() throws IOException {
        //prepare
        Cookie[] cookies = {new Cookie("user-token", "1111")};
        when(request.getCookies()).thenReturn(cookies);
        //when
        logoutServlet.doGet(request, response);
        //then
        verify(request).getCookies();
        verify(response).sendRedirect("/login");
    }
}