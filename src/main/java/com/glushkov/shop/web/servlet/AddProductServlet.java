package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.AuthenticationService;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class AddProductServlet extends HttpServlet {
    private final String CONTENT_TYPE = "text/html;charset=utf-8";
    private ProductService productService = ServiceLocator.getService("productService");
    private AuthenticationService authenticationService = ServiceLocator.getService("authenticationService");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        boolean isAuth = authenticationService.isAdmin(cookies);
        response.setContentType(CONTENT_TYPE);

        if (isAuth) {
            PageGenerator.instance().process("add-product", response.getWriter());
        } else {
            val parameters = new HashMap<String, Object>();
            parameters.put("message", "Access denied. Please login and try again.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PageGenerator.instance().process("login", parameters, response.getWriter());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val product = Product.builder()
                .name(request.getParameter("name"))
                .description("description")
                .image(request.getParameter("image"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();

        productService.save(product);
        response.sendRedirect("/");
    }
}
