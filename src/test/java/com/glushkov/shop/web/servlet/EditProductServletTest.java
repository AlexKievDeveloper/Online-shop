package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;

class EditProductServletTest {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ProductService productService;
    private final EditProductServlet editProductServlet;

    EditProductServletTest() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        productService = mock(ProductService.class);
        editProductServlet = new EditProductServlet(productService);
    }

    //TODO как написать тест? Product.getID() возвращает null. Product локальная переменная метода(нельзя замокать).
/*    @Test
    @DisplayName("Processes the request and sends a page with product request form")
    void doGetTest() throws IOException {
        //prepare
        val writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("id")).thenReturn("1");
        //when
        editProductServlet.doGet(request, response);
        //then
        verify(request).getParameter("id");
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).sendRedirect("/product");
        verify(response).getWriter();
    }*/

    @Test
    @DisplayName("Process the request and updating product in data base")
    void doPostTest() throws IOException {
        //prepare
        val product = Product.builder()
                .id(1)
                .name("product")
                .description("description")
                .image("image")
                .price(299.01)
                .build();

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("product");
        when(request.getParameter("price")).thenReturn("299.01");
        when(request.getParameter("image")).thenReturn("image");

        //when
        editProductServlet.doPost(request, response);
        //then
        verify(request).getParameter("id");
        verify(request).getParameter("name");
        verify(request).getParameter("price");
        verify(request).getParameter("image");
        verify(productService).update(product);
        verify(response).sendRedirect("/home");
    }

    @Test
    @DisplayName("Put products fields into parameter map")
    void putProductFieldsIntoParameterMap() {
        //prepare
        val parameters = mock(Map.class);
        val product = mock(Product.class);
        when(product.getId()).thenReturn(1);
        when(product.getName()).thenReturn("product");
        when(product.getPrice()).thenReturn(299.01);
        when(product.getImage()).thenReturn("image");

        //when
        editProductServlet.putProductFieldsIntoParameterMap(product, parameters);
        //then
        verify(product).getId();
        verify(product).getName();
        verify(product).getPrice();
        verify(product).getImage();
        verify(parameters).put("id", product.getId());
        verify(parameters).put("name", product.getName());
        verify(parameters).put("price", product.getPrice());
        verify(parameters).put("image", product.getImage());
    }
}