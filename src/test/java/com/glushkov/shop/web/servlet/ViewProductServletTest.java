package com.glushkov.shop.web.servlet;

import com.glushkov.shop.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewProductServletTest {
    //TODO как написать тест? Product.getID() возвращает null
/*   @Test
    @DisplayName("")
    void doGetTest() throws IOException {
        //prepare
        val request = mock(HttpServletRequest.class);
        val response = mock(HttpServletResponse.class);
        val productService = mock(ProductService.class);
        val viewProductServlet = new ViewProductServlet(productService);
        val writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("id")).thenReturn("1");
        //when
        viewProductServlet.doGet(request, response);
        //then
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }*/
}