package com.glushkov.shop.web.servlet;

import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class RegistrationServletTest {
    @Mock
    private SecurityService securityService;
    @Mock
    private UserService userService;
    @InjectMocks
    private RegistrationServlet registrationServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private Session session;

    @Test
    @DisplayName("Returns page with registration form")
    void doGetTest() throws IOException {
        //prepare
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("message")).thenReturn("hello");
        //when
        registrationServlet.doGet(request, response);
        //then
        verify(response).setContentType("text/html;charset=utf-8");
        verify(request).getParameter("message");
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Saving user to DB")
    void doPostTest() throws IOException {
        //prepare
        when(session.getToken()).thenReturn("1111");
        when(securityService.login("Alex", "1111")).thenReturn(session);
        when(request.getParameter("password")).thenReturn("1111");
        when(request.getParameter("login")).thenReturn("Alex");
        //when
        registrationServlet.doPost(request, response);
        //then
        verify(request).getParameter("login");
        verify(userService).isLoginExist("Alex");
        verify(request).getParameter("password");
        verify(userService).save(any());
        verify(securityService).login("Alex", "1111");
        verify(response).addCookie(any());
        verify(response).sendRedirect("/");
    }
}