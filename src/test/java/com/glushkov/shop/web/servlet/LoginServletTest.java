package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.service.UserService;
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
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class LoginServletTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private LoginServlet loginServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    @Test
    @DisplayName("Processes the request and sends a page with login form")
    void doGetTest() throws IOException {
        //prepare
        LoginServlet loginServlet = new LoginServlet();
        when(response.getWriter()).thenReturn(printWriter);
        //when
        loginServlet.doGet(request, response);
        //then
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Redirect to home page if user == null")
    void doPostIfUserIsExistTest() throws IOException {
        //prepare
        User user = User.builder().id(1).login("Alex").password("1111111").role(Role.ADMIN).build();
        when(request.getParameter("login")).thenReturn("Alex");
        when(request.getParameter("password")).thenReturn("1111111");
        when(userService.findUser("Alex", "1111111")).thenReturn(user);
        //when
        loginServlet.doPost(request, response);
        //then
        verify(request).getParameter("login");
        verify(request).getParameter("password");
        verify(response).addCookie(any());
        verify(userService).findUser(anyString(), anyString());
        verify(response).sendRedirect("/");
    }

    @Test
    @DisplayName("Redirect to home page if user == null")
    void doPostIfUserIsNullTest() throws IOException {
        //prepare
        when(request.getParameter("login")).thenReturn("Alex");
        when(request.getParameter("password")).thenReturn("1111");
        when(userService.findUser(anyString(), anyString())).thenReturn(null);
        when(response.getWriter()).thenReturn(printWriter);
        //when
        loginServlet.doPost(request, response);
        //then
        verify(request).getParameter("login");
        verify(request).getParameter("password");
        verify(response).addCookie(any());
        verify(userService).findUser(anyString(), anyString());
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).getWriter();
    }
}