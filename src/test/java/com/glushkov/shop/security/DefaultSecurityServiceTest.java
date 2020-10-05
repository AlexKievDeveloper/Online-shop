package com.glushkov.shop.security;

import com.glushkov.shop.entity.User;
import com.glushkov.shop.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class DefaultSecurityServiceTest {
    private Session session;
    private ArrayList<Session> sessionList;
    @Mock
    private UserService userService;
    @InjectMocks
    private DefaultSecurityService securityService;
    @Mock
    private User user;

    DefaultSecurityServiceTest() {

        session = Session.builder()
                .token("1111")
                .expireDate(LocalDateTime.now().plusSeconds(60))
                .build();
        sessionList = new ArrayList<>();
        sessionList.add(session);
    }

    @Test
    @DisplayName("Login user and return session")
    void login() {
        //prepare
        when(userService.findUserByLogin("Alex")).thenReturn(user);
        when(user.getSole()).thenReturn("sole");
        when(user.getPassword()).thenReturn("1111");
        //when
        securityService.login("Alex", "1111");
        //then
        verify(userService).findUserByLogin("Alex");
        verify(user).getSole();
        verify(user).getPassword();
    }

    @Test
    @DisplayName("Removing session from session list")
    void logout() {
        //prepare
        securityService.setSessionList(sessionList);
        assertEquals(session, securityService.getSession("1111"));
        //when
        securityService.logout("1111");
        //then
        assertNull(securityService.getSession("1111"));
    }

    @Test
    @DisplayName("Returns session from sessions list")
    void getSession() {
        //prepare
        securityService.setSessionList(sessionList);
        //when
        Session actual = securityService.getSession("1111");
        //then
        assertEquals(session, actual);
    }
}