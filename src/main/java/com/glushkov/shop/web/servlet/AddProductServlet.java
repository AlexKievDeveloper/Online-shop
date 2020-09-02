package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProductServlet extends HttpServlet {

    private final ProductService productService;

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Product> productList = productService.findAll(); //список в мапу, мапу в генератор страниц .страницу в респонс
/*        Map<String, Object> productsMap = new HashMap<>();
        productsMap.put("users", productList);*/

        WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.process("/home.html", ctx, response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){

    }
}
