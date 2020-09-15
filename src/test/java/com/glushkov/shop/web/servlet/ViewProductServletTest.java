package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class ViewProductServletTest {
    @Test
    @DisplayName("Processed the request and send response with product page")
    void doGetTest() throws IOException {
        //prepare
        val request = mock(HttpServletRequest.class);
        val response = mock(HttpServletResponse.class);
        val productService = mock(ProductService.class);
        val viewProductServlet = new ViewProductServlet(productService);
        val writer = mock(PrintWriter.class);
        val product = mock(Product.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("id")).thenReturn("1");
        when(productService.findById(1)).thenReturn(product);
        //when
        viewProductServlet.doGet(request, response);
        //then
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }
}