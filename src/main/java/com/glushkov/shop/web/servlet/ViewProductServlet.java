package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.impl.DefaultProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.glushkov.shop.web.WebConstants.CONTENT_TYPE;

public class ViewProductServlet extends HttpServlet {
    private DefaultProductService productService = ServiceLocator.getService("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val role = ((Session) request.getAttribute("session")).getUser().getRole();
        val product = productService.findById(Integer.parseInt(request.getPathInfo().substring(1)));
        val productMap = new HashMap<String, Object>();
        putProductFieldsIntoParameterMap(product, productMap);
        response.setContentType(CONTENT_TYPE);

        if (role != null) {
            productMap.put("role", role.getUserRole());
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
