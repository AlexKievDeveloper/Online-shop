package com.glushkov.shop.web.handler;

import lombok.val;
import org.eclipse.jetty.server.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class DefaultErrorHandlerTest {

    @Test
    @DisplayName("Generates a response to the client if an error occurs during request processing")
    void generateAcceptableResponseTest() throws IOException {
        //prepare
        val defaultErrorHandler = new DefaultErrorHandler();

        val mockBaseRequest = mock(Request.class);

        val mockRequest = mock(HttpServletRequest.class);
        val mockResponse = mock(HttpServletResponse.class);

        val stringWriter = new StringWriter();
        when(mockResponse.getWriter()).thenReturn(new PrintWriter(stringWriter));

        when(mockRequest.getAttribute("javax.servlet.error.servlet_name")).
                thenReturn("com.glushkov.shop.web.servlet.AddProductServlet");
        when(mockRequest.getAttribute("javax.servlet.error.exception")).
                thenReturn(new SQLException());

        int code = 500;
        val message = "SQLException";
        val mimeType = "text/html";

        //when
        defaultErrorHandler.generateAcceptableResponse(mockBaseRequest, mockRequest, mockResponse, code, message, mimeType);

        //then
        verify(mockBaseRequest).setHandled(true);
        verify(mockResponse).setContentType("text/html;charset=utf-8");
        verify(mockResponse).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(mockResponse).getWriter();
    }
}