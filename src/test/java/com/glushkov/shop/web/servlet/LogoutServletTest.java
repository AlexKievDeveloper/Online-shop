/*
package com.glushkov.shop.web.servlet;

import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {
    @Mock
    SecurityService securityService;
    @InjectMocks
    private LogoutServlet logoutServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Session session;

    @Test
    @DisplayName("Removing session from sessions list")
    void doGetTest() throws IOException {
        //prepare
        when(request.getAttribute("session")).thenReturn(session);
        //when
        logoutServlet.doGet(request, response);
        //then
        verify(request).getAttribute("session");
        verify(securityService).logout(any());
        verify(response).sendRedirect("/login");
    }
}*/
