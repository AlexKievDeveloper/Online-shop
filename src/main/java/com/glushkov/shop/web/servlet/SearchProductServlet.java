package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductServlet extends HttpServlet {

    private final ProductService productService;

    public SearchProductServlet(ProductService productService) {
        this.productService = productService;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PageGenerator pageGenerator = PageGenerator.instance();
        String enteredName = request.getParameter("enteredName");
        List<Product> usersList = productService.findByName(enteredName);
        Map<String, Object> productsMap = new HashMap<>();

        if (usersList.isEmpty()) {
            productsMap.put("message", "Sorry, no products were found for your request: " + enteredName);
        } else {
            productsMap.put("products", usersList);
        }

        response.setContentType("text/html;charset=utf-8");

        pageGenerator.process("/search", productsMap, response.getWriter());
    }
}
