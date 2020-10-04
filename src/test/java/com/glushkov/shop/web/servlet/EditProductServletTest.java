package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.impl.DefaultProductService;
import lombok.val;
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
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditProductServletTest {
    @Mock
    private DefaultProductService productService;
    @InjectMocks
    private EditProductServlet editProductServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Product product;
    @Mock
    private PrintWriter printWriter;

    @Test
    @DisplayName("Processes the request and sends a page with product request form")
    void doGetTest() throws IOException {
        //prepare
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getPathInfo()).thenReturn("/1");
        when(productService.findById(1)).thenReturn(product);
        //when
        editProductServlet.doGet(request, response);
        //then
        verify(response).setContentType("text/html;charset=utf-8");
        verify(request).getPathInfo();
        verify(productService).findById(1);
        verify(response).getWriter();
    }

    @Test
    @DisplayName("Process the request and updating product in data base")
    void doPostTest() throws IOException {
        //prepare
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
        verify(productService).update(any());
        verify(response).sendRedirect("/");
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
