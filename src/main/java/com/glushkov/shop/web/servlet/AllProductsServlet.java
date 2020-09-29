package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.service.impl.DefaultAuthenticationService;
import com.glushkov.shop.service.impl.DefaultProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class AllProductsServlet extends HttpServlet {
    private final String contentType = "text/html;charset=utf-8";
    private DefaultProductService productService = ServiceLocator.getService("productService");
    private DefaultAuthenticationService defaultAuthenticationService = ServiceLocator
            .getService("authenticationService");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        boolean isAuth = defaultAuthenticationService.isUserOrAdmin(cookies);
        response.setContentType(contentType);

        if (isAuth) {
            val map = new HashMap<String, Object>();
            val productList = productService.findAll();
            map.put("products", productList);

            PageGenerator.instance().process("home", map, response.getWriter());
        } else {
            val parameters = new HashMap<String, Object>();
            parameters.put("message", "Access denied. Please login and try again.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PageGenerator.instance().process("login", parameters, response.getWriter());
        }
    }
}
