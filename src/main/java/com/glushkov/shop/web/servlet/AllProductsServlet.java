package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.service.impl.DefaultProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.glushkov.shop.web.WebConstants.CONTENT_TYPE;

public class AllProductsServlet extends HttpServlet {
    private DefaultProductService productService = ServiceLocator.getService("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);

        val map = new HashMap<String, Object>();
        val productList = productService.findAll();
        map.put("products", productList);

        PageGenerator.instance().process("home", map, response.getWriter());
    }
}
