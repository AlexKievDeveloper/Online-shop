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
import java.util.Map;

public class EditProductServlet extends HttpServlet {
    private final String CONTENT_TYPE = "text/html;charset=utf-8";
    private ProductService productService = ServiceLocator.getService("productService");
    private AuthenticationService authenticationService = ServiceLocator.getService("authenticationService");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        boolean isAuth = authenticationService.isAdmin(cookies);
        response.setContentType(CONTENT_TYPE);

        if (isAuth) {
            val product = productService.findById(Integer.parseInt(request.getPathInfo().substring(1)));
            val productMap = new HashMap<String, Object>();
            putProductFieldsIntoParameterMap(product, productMap);

            PageGenerator.instance().process("edit", productMap, response.getWriter());
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
                .id(Integer.parseInt(request.getParameter("id")))
                .name(request.getParameter("name"))
                .description("description")
                .image(request.getParameter("image"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();

        productService.update(product);
        response.sendRedirect("/");
    }

    void putProductFieldsIntoParameterMap(Product product, Map<String, Object> parameters) {
        parameters.put("id", product.getId());
        parameters.put("name", product.getName());
        parameters.put("image", product.getImage());
        parameters.put("price", product.getPrice());
    }

}

