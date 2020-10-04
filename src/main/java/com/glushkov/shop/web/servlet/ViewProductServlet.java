package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.entity.Role;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.service.impl.DefaultProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.glushkov.shop.web.WebConstants.CONTENT_TYPE;

public class ViewProductServlet extends HttpServlet {
    private DefaultProductService productService = ServiceLocator.getService("productService");
    private SecurityService securityService = ServiceLocator.getService("securityService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        Role role = null;

        for (Cookie cookie : cookies) {
            if ("user-token".equals(cookie.getName())) {
                role = securityService.getSession(cookie.getValue()).getUser().getRole();
            }
        }
        response.setContentType(CONTENT_TYPE);

        val product = productService.findById(Integer.parseInt(request.getPathInfo().substring(1)));
        val productMap = new HashMap<String, Object>();
        putProductFieldsIntoParameterMap(product, productMap);

        if (role != null) {
            if (role.equals(Role.ADMIN)) {
                productMap.put("role", "ADMIN");
            } else if (role.equals(Role.USER)) {
                productMap.put("role", "USER");
            }
            PageGenerator.instance().process("view", productMap, response.getWriter());
        } else {
            response.sendRedirect("/login?message=Access denied. Please login and try again.");
        }
    }

    void putProductFieldsIntoParameterMap(Product product, Map<String, Object> parameters) {
        parameters.put("id", product.getId());
        parameters.put("name", product.getName());
        parameters.put("image", product.getImage());
        parameters.put("price", product.getPrice());
    }
}
