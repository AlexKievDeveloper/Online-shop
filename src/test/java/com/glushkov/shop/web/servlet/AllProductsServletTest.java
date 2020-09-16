package com.glushkov.shop.web.servlet;

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
class AllProductsServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ProductService productService;
    @Mock
    private PrintWriter writer;

    @Test
    @DisplayName("Processes the request and sends a response with home page and all products there")
    void doGetTest() throws IOException {
        //prepare
        val allProductServlet = new AllProductsServlet(productService);
        //val writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        //when
        allProductServlet.doGet(request, response);
        //then
        verify(response).getWriter();
    }
}