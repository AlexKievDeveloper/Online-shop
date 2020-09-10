package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {

    private final ProductService productService;

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val pageGenerator = PageGenerator.instance();
        pageGenerator.process("/add-product", response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        val product = Product.builder()
                .name(request.getParameter("name"))
                .description("description")
                .image(request.getParameter("image"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();

        productService.save(product);
        response.sendRedirect("/home");
    }
}
