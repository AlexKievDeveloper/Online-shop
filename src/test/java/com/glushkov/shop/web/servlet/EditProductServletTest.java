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
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditProductServletTest {
    private final ProductService productService;
    private final EditProductServlet editProductServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    EditProductServletTest() {
        productService = mock(ProductService.class);
        editProductServlet = new EditProductServlet(/*productService*/);
    }

/*    @Test
    @DisplayName("Processes the request and sends a page with product request form")
    void doGetTest() throws IOException {
        //prepare
        val writer = mock(PrintWriter.class);
        val product = mock(Product.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getPathInfo()).thenReturn("/1");
        *//*when(productService.findById(1)).thenReturn(product);*//*

        //when
        editProductServlet.doGet(request, response);
        //then
        verify(request).getPathInfo();
        verify(response).setContentType("text/html;charset=utf-8");
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
       /* verify(productService).update(product);*/
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