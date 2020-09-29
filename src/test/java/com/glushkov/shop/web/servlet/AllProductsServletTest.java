package com.glushkov.shop.web.servlet;

import com.glushkov.shop.service.AuthenticationService;
import com.glushkov.shop.service.ProductService;
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
class AllProductsServletTest {
    @Mock
    private ProductService productService;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AllProductsServlet allProductsServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    @Test
    @DisplayName("Processes the request and sends a response with home page and all products there")
    void doGetTest() throws IOException {
        when(authenticationService.isUserOrAdmin(any())).thenReturn(true);
        when(response.getWriter()).thenReturn(printWriter);
        //when
        allProductsServlet.doGet(request, response);
        //then
        verify(authenticationService).isUserOrAdmin(any());
        verify(productService).findAll();
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Processes the request and sends a response with login page(user not unauthorized")
    void doGetUserNotAuthorizedTest() throws IOException {
        when(authenticationService.isUserOrAdmin(any())).thenReturn(false);
        when(response.getWriter()).thenReturn(printWriter);
        //when
        allProductsServlet.doGet(request, response);
        //then
        verify(authenticationService).isUserOrAdmin(any());
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).getWriter();
    }
}
