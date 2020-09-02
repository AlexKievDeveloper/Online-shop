package com.glushkov.shop.web.servlet;

import com.glushkov.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllProductsServlet extends HttpServlet {

    private final ProductService productService;

    public AllProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
        String template = new String(getClass().getResourceAsStream("/templates/home.html").readAllBytes());
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.process(template, ctx, response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
