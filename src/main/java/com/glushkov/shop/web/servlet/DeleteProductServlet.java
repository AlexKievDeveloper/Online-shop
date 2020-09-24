package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.service.ProductService;
import lombok.SneakyThrows;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductServlet extends HttpServlet {
    private final ProductService productService;

    public DeleteProductServlet() {
        this.productService = (ProductService) ServiceLocator.getService("productService");
    }

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        val id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        response.sendRedirect("/home");
    }
}
