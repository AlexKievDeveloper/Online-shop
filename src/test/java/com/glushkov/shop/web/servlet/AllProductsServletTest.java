package com.glushkov.shop.web.servlet;

import com.glushkov.shop.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class AllProductsServletTest {

    @Test
    @DisplayName("Processes the request and sends a response with home page and all products there")
    void doGetTest() throws IOException {
        //prepare
        val request = mock(HttpServletRequest.class);
        val response = mock(HttpServletResponse.class);
        val productService = mock(ProductService.class);
        val allProductServlet = new AllProductsServlet(productService);
        val writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        //when
        allProductServlet.doGet(request, response);
        //then
        verify(response).getWriter();
    }
}