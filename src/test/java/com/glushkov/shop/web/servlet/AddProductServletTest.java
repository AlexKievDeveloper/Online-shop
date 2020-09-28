package com.glushkov.shop.web.servlet;

import com.glushkov.shop.service.ProductService;
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

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class AddProductServletTest {
    @Mock
    private ProductService productService;
    @InjectMocks
    private AddProductServlet addProductServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

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
        when(request.getParameter("name")).thenReturn("product");
        when(request.getParameter("image")).thenReturn("http://images.com/img.png");
        when(request.getParameter("price")).thenReturn("299.01");
        //when
        addProductServlet.doPost(request, response);
        //then
        verify(request).getParameter("name");
        verify(request).getParameter("image");
        verify(request).getParameter("price");
        verify(response).sendRedirect("home");
    }
}
