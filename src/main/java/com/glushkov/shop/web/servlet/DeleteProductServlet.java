package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.service.impl.DefaultProductService;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private DefaultProductService productService = ServiceLocator.getService("productService");

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        response.sendRedirect("/");
    }
}
