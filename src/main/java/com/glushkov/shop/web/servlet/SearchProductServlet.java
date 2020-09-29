package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
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

public class SearchProductServlet extends HttpServlet {
    private final String contentType = "text/html;charset=utf-8";
    private ProductService productService = ServiceLocator.getService("productService");
    private AuthenticationService authenticationService = ServiceLocator.getService("authenticationService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        boolean isAuth = authenticationService.isUserOrAdmin(cookies);
        response.setContentType(contentType);

        if (isAuth) {
            val enteredName = request.getParameter("enteredName");
            val usersList = productService.findByName(enteredName);
            val productsMap = new HashMap<String, Object>();

            if (usersList.isEmpty()) {
                productsMap.put("message", "Sorry, no products were found for your request: " + enteredName);
            } else {
                productsMap.put("products", usersList);
            }

            PageGenerator.instance().process("search", productsMap, response.getWriter());
        } else {
            val parameters = new HashMap<String, Object>();
            parameters.put("message", "Access denied. Please login and try again.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PageGenerator.instance().process("login", parameters, response.getWriter());
        }
    }
}
