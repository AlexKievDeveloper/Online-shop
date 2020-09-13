package com.glushkov.shop.web.handler;

import com.glushkov.shop.web.templater.PageGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
public class DefaultErrorHandler extends ErrorPageErrorHandler {

    @SneakyThrows
    protected void generateAcceptableResponse(Request baseRequest, HttpServletRequest request,
                                              HttpServletResponse response, int code, String message, String mimeType) {

        val parameterMap = new HashMap<String, Object>();

        val pageGenerator = PageGenerator.instance();

        val servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        val exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        log.error("Error in servlet: {}, code: {}, message: {}", servletName, code, message, exception);

        baseRequest.setHandled(true);

        parameterMap.put("code", code);
        parameterMap.put("message", message);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        pageGenerator.process("/error.html", parameterMap, response.getWriter());
    }
}
