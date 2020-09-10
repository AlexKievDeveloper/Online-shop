package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AllProductsServlet extends HttpServlet {

    private final ProductService productService;

    public AllProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        val map = new HashMap<String, Object>();
        List<Product> productList = productService.findAll();
        map.put("products", productList);

        val pageGenerator = PageGenerator.instance();
        pageGenerator.process("/index", map, response.getWriter());
    }
}
