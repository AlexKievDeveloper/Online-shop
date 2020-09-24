/*
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProductServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ProductService productService;

    @Test
    @DisplayName("Deleting product by id and redirect to /home")
    void doPostTest() throws IOException {
        //prepare
        val deleteProductServlet = new DeleteProductServlet(*/
/*productService*//*
);

        when(request.getParameter("id")).thenReturn("1");
        //when
        deleteProductServlet.doPost(request, response);
        //then
        verify(request).getParameter("id");
        verify(response).sendRedirect("/home");
    }
}*/
