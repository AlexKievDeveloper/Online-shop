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

class AddProductServletTest {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ProductService productService;
    private final AddProductServlet addProductServlet;

    AddProductServletTest() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        productService = mock(ProductService.class);
        addProductServlet = new AddProductServlet(productService);
    }


    @Test
    @DisplayName("Processes the request and sends a response page with a added form")
    void doGetTest() throws IOException {
        //prepare
        val writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        //when
        addProductServlet.doGet(request, response);
        //then
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Processes the request and saving user to database")
    void doPostTest() throws IOException {
        //prepare
        val product = Product.builder()
                .name("product")
                .description("description")
                .image("http://images.com/img.png")
                .price(299.01)
                .build();

        when(request.getParameter("name")).thenReturn("product");
        when(request.getParameter("image")).thenReturn("http://images.com/img.png");
        when(request.getParameter("price")).thenReturn("299.01");
        //when
        addProductServlet.doPost(request, response);
        //then
        verify(request).getParameter("name");
        verify(request).getParameter("image");
        verify(request).getParameter("price");
        verify(productService).save(product);
        verify(response).sendRedirect("/home");
    }
}