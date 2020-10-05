package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.security.Session;
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
class ViewProductServletTest {
    @Mock
    private DefaultProductService productService;
    @InjectMocks
    private ViewProductServlet viewProductServlet;
    @Mock
    private Product product;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter writer;
    @Mock
    User user;
    @Mock
    Session session;

    @Test
    @DisplayName("Processed the request and send response with product page")
    void doGetTest() throws IOException {
        //prepare
        when(request.getAttribute("session")).thenReturn(session);
        when(user.getRole()).thenReturn(Role.ADMIN);
        when(session.getUser()).thenReturn(user);
        when(request.getPathInfo()).thenReturn("/1");
        when(productService.findById(1)).thenReturn(product);
        when(response.getWriter()).thenReturn(writer);
        //when
        viewProductServlet.doGet(request, response);
        //then
        verify(request).getAttribute("session");
        verify(session).getUser();
        verify(productService).findById(1);
        verify(response).setContentType("text/html;charset=utf-8");
        verify(request).getPathInfo();
        verify(response).getWriter();
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
        viewProductServlet.putProductFieldsIntoParameterMap(product, parameters);
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
