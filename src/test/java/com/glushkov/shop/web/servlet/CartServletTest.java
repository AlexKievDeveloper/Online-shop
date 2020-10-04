package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.security.impl.DefaultSecurityService;
import com.glushkov.shop.service.impl.DefaultProductService;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServletTest {
    @Mock
    private DefaultProductService productService;
    @Mock
    private DefaultSecurityService securityService;
    @InjectMocks
    private CartServlet cartServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter writer;
    @Mock
    Product product;

    @Test
    @DisplayName("Returns page with table of purchases")
    void doGetTest() throws IOException {
        //prepare
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Session session = Session.builder()
                .cart(productList)
                .build();

        Cookie[] cookies = {new Cookie("user-token", "1111")};
        when(request.getCookies()).thenReturn(cookies);
        when(securityService.getSession("1111")).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        //when
        cartServlet.doGet(request, response);

        //then
        verify(request).getCookies();
        verify(securityService).getSession("1111");
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Added product to purchases list")
    void doPostTest() throws IOException {
        //prepare
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Session session = Session.builder()
                .cart(productList)
                .build();
        Cookie[] cookies = {new Cookie("user-token", "1111")};
        when(request.getCookies()).thenReturn(cookies);
        when(request.getParameter("id")).thenReturn("1");
        when(productService.findById(1)).thenReturn(product);
        when(securityService.getSession("1111")).thenReturn(session);
        //when
        cartServlet.doPost(request, response);
        //then
        verify(request).getParameter("id");
        verify(request).getCookies();
        verify(productService).findById(1);
        verify(securityService).getSession("1111");
        verify(response).sendRedirect("/");
    }
}