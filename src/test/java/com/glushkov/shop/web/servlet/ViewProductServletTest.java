package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViewProductServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ProductService productService;
    @Mock
    private PrintWriter writer;
    @Mock
    private Product product;

/*    @Test
    @DisplayName("Processed the request and send response with product page")
    void doGetTest() throws IOException {
        //prepare
        val viewProductServlet = new ViewProductServlet(*//*productService*//*);
        when(response.getWriter()).thenReturn(writer);
        when(request.getPathInfo()).thenReturn("/1");
        *//*when(productService.findById(1)).thenReturn(product);*//*
        //when
        viewProductServlet.doGet(request, response);
        //then
        verify(request).getPathInfo();
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }*/
}