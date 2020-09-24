package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.SneakyThrows;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class AllProductsServlet extends HttpServlet {

    private final ProductService productService;

    public AllProductsServlet() {
        this.productService = (ProductService) ServiceLocator.getService("productService");
    }

    @SneakyThrows
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        val map = new HashMap<String, Object>();
        List<Product> productList = productService.findAll();
        map.put("products", productList);

        val pageGenerator = PageGenerator.instance();
        response.setContentType("text/html;charset=utf-8");
        pageGenerator.process("/home", map, response.getWriter());
    }
}
