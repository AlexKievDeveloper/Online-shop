package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Product;
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
import java.util.List;

import static com.glushkov.shop.web.WebConstants.CONTENT_TYPE;

public class CartServlet extends HttpServlet {
    private SecurityService securityService = ServiceLocator.getService("securityService");
    private DefaultProductService productService = ServiceLocator.getService("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        List<Product> purchaseList;
        val map = new HashMap<String, Object>();

        for (Cookie cookie : cookies) {
            if ("user-token".equals(cookie.getName())) {
                purchaseList = securityService.getSession(cookie.getValue()).getCart();

                if (!purchaseList.isEmpty()) {
                    double totalCost = 0;
                    for (Product product : purchaseList) {
                        totalCost = totalCost + product.getPrice();
                    }

                    map.put("total_cost", totalCost);
                    map.put("purchases", purchaseList);
                }
            }
        }

        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("cart", map, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("user-token".equals(cookie.getName())) {
                securityService.getSession(cookie.getValue()).getCart().add(product);
            }
        }
        response.sendRedirect("/");
    }
}
