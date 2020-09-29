package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.entity.Role;
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
import java.util.Map;

public class ViewProductServlet extends HttpServlet {
    private final String contentType = "text/html;charset=utf-8";
    private ProductService productService = ServiceLocator.getService("productService");
    private AuthenticationService authenticationService = ServiceLocator.getService("authenticationService");
    private Map<String, Role> tokensRoleMap = AuthenticationService.getTokensRoleMap();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        response.setContentType(contentType);

        boolean isAuth = authenticationService.isUserOrAdmin(cookies);

        if (isAuth) {
            val validCookie = authenticationService.getValidCookie(cookies);
            val product = productService.findById(Integer.parseInt(request.getPathInfo().substring(1)));
            val productMap = new HashMap<String, Object>();
            putProductFieldsIntoParameterMap(product, productMap);

            if (tokensRoleMap.get(validCookie.getValue()).equals(Role.ADMIN)) {
                PageGenerator.instance().process("admin-view", productMap, response.getWriter());
            } else if (tokensRoleMap.get(validCookie.getValue()).equals(Role.USER)) {
                PageGenerator.instance().process("view", productMap, response.getWriter());
            }
        } else {
            val parameters = new HashMap<String, Object>();
            parameters.put("message", "Access denied. Please login and try again.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PageGenerator.instance().process("login", parameters, response.getWriter());
        }
    }

    void putProductFieldsIntoParameterMap(Product product, Map<String, Object> parameters) {
        parameters.put("id", product.getId());
        parameters.put("name", product.getName());
        parameters.put("image", product.getImage());
        parameters.put("price", product.getPrice());
    }
}
