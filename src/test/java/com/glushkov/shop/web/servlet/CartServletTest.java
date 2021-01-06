
/*package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.security.Session;
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
import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServletTest {
    @Mock
    private DefaultProductService productService;
    @InjectMocks
    private CartServlet cartServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter writer;
    @Mock
    private Product product;
    @Mock
    private Session session;

    @Test
    @DisplayName("Returns page with table of purchases")
    void doGetTest() throws IOException {
        //prepare
        when(request.getAttribute("session")).thenReturn(session);
        when(session.getCart()).thenReturn(new ArrayList<>());
        when(response.getWriter()).thenReturn(writer);
        //when
        cartServlet.doGet(request, response);
        //then
        verify(request).getAttribute("session");
        verify(session).getCart();
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Added product to purchases list")
    void doPostTest() throws IOException {
        //prepare
        when(request.getAttribute("session")).thenReturn(session);
        when(request.getParameter("id")).thenReturn("1");
        when(productService.findById(1)).thenReturn(product);
        //when
        cartServlet.doPost(request, response);
        //then
        verify(request).getAttribute("session");
        verify(request).getParameter("id");
        verify(productService).findById(1);
        verify(response).sendRedirect("/");
    }
}*/
