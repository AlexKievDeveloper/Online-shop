package com.glushkov.shop.web.servlet;

import com.glushkov.shop.service.impl.DefaultAuthenticationService;
import com.glushkov.shop.service.impl.DefaultProductService;
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

@ExtendWith(MockitoExtension.class)
class SearchProductServletTest {
    @Mock
    private DefaultProductService productService;
    @Mock
    private DefaultAuthenticationService defaultAuthenticationService;
    @InjectMocks
    private SearchProductServlet searchProductServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter writer;

    @Test
    @DisplayName("Processed the request and and send response page with message or products")
    void doGetTest() throws IOException {
        //prepare
        when(defaultAuthenticationService.isUserOrAdmin(any())).thenReturn(true);
        when(response.getWriter()).thenReturn(writer);
        //when
        searchProductServlet.doGet(request, response);
        //then
        verify(defaultAuthenticationService).isUserOrAdmin(any());
        verify(request).getParameter("enteredName");
        verify(productService).findByName(any());
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Processed the request and and send response page with login form")
    void doGetIfUserUnauthorizedTest() throws IOException {
        //prepare
        when(defaultAuthenticationService.isUserOrAdmin(any())).thenReturn(false);
        when(response.getWriter()).thenReturn(writer);
        //when
        searchProductServlet.doGet(request, response);
        //then
        verify(defaultAuthenticationService).isUserOrAdmin(any());
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).getWriter();
    }
}
