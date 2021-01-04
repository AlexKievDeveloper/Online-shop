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
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class LoginServletTest {
    @Mock
    private SecurityService securityService;
    @InjectMocks
    private LoginServlet loginServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private Session session;

    @Test
    @DisplayName("Processes the request and sends a page with login form")
    void doGetTest() throws IOException {
        //prepare
        when(response.getWriter()).thenReturn(printWriter);
        //when
        loginServlet.doGet(request, response);
        //then
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Redirect to home page if session != null")
    void doPostIfSessionExistTest() throws IOException {
        //prepare
        when(request.getParameter("login")).thenReturn("Alex");
        when(request.getParameter("password")).thenReturn("1111111");
        when(securityService.login("Alex", "1111111")).thenReturn(session);
        //when
        loginServlet.doPost(request, response);
        //then
        verify(request).getParameter("login");
        verify(request).getParameter("password");
        verify(response).addCookie(any());
        verify(response).sendRedirect("/");
    }

    @Test
    @DisplayName("Redirect to login page if session == null")
    void doPostIfSessionNotExistTest() throws IOException {
        //prepare
        when(request.getParameter("login")).thenReturn("Alex");
        when(request.getParameter("password")).thenReturn("1111");
        when(securityService.login("Alex", "1111")).thenReturn(null);
        //when
        loginServlet.doPost(request, response);
        //then
        verify(request).getParameter("login");
        verify(request).getParameter("password");
        verify(securityService).login("Alex", "1111");
        verify(response).sendRedirect("/login?message=Access denied. Please login and try again.");
    }
}