package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.impl.DefaultProductService;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchProductServletTest {
    @Mock
    private DefaultProductService productService;
    @InjectMocks
    private SearchProductServlet searchProductServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter writer;
    @Mock
    private Product product;

    @Test
    @DisplayName("Processed the request and send response page with message or products")
    void doGetTest() throws IOException {
        //prepare
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.findByName(any())).thenReturn(productList);
        when(response.getWriter()).thenReturn(writer);
        //when
        searchProductServlet.doGet(request, response);
        //then
        verify(request).getParameter("enteredName");
        verify(productService).findByName(any());
        verify(response).setContentType("text/html;charset=utf-8");
        verify(response).getWriter();
    }
}
