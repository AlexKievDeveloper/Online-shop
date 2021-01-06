/*
package com.glushkov.shop.web.servlet;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProductServletTest {
    @Mock
    private DefaultProductService productService;
    @InjectMocks
    private DeleteProductServlet deleteProductServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("Deleting product by id and redirect to /home")
    void doPostTest() throws IOException {
        //prepare
        when(request.getParameter("id")).thenReturn("1");
        //when
        deleteProductServlet.doPost(request, response);
        //then
        verify(productService).delete(1);
        verify(request).getParameter("id");
        verify(response).sendRedirect("/");
    }
}
*/
