package com.glushkov.shop.controller;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerEntity implements ControllerInterface{
    public ControllerEntity() {
        super();
    }

    public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {

        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        templateEngine.process("userprofile", ctx, response.getWriter());

    }
}
