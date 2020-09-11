package com.glushkov.shop.web.servlet;

import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.SneakyThrows;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class SearchProductServlet extends HttpServlet {

    private final ProductService productService;

    public SearchProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        val pageGenerator = PageGenerator.instance();
        val enteredName = request.getParameter("enteredName");
        val usersList = productService.findByName(enteredName);
        val productsMap = new HashMap<String, Object>();

        if (usersList.isEmpty()) {
            productsMap.put("message", "Sorry, no products were found for your request: " + enteredName);
        } else {
            productsMap.put("products", usersList);
        }

        response.setContentType("text/html;charset=utf-8");

        pageGenerator.process("/search", productsMap, response.getWriter());
    }
}
