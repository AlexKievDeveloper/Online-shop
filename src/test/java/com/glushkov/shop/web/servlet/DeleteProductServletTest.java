package com.glushkov.shop.web.servlet;

import com.glushkov.shop.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

class DeleteProductServletTest {

    @Test
    @DisplayName("Deleting product by id and redirect to /home")
    void doPostTest() throws IOException {
        //prepare
        val request = mock(HttpServletRequest.class);
        val response = mock(HttpServletResponse.class);
        val productService = mock(ProductService.class);
        val deleteProductServlet = new DeleteProductServlet(productService);

        when(request.getParameter("id")).thenReturn("1");
        //when
        deleteProductServlet.doPost(request, response);
        //then
        verify(request).getParameter("id");
        verify(response).sendRedirect("/home");
    }
}