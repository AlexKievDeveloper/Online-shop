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
import java.util.Map;

public class EditProductServlet extends HttpServlet {
    private final ProductService productService;

    public EditProductServlet(ProductService productService) {
        this.productService = productService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val pageGenerator = PageGenerator.instance();
        val product = productService.findById(Integer.parseInt(request.getParameter("id")));

        val productMap = new HashMap<String, Object>();
        putProductFieldsIntoParameterMap(product, productMap);

        response.setContentType("text/html;charset=utf-8");
        pageGenerator.process("/product", productMap, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        val product = Product.builder()
                .id(Integer.parseInt(request.getParameter("id")))
                .name(request.getParameter("name"))
                .description("description")
                .image(request.getParameter("image"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();

        productService.update(product);
        response.sendRedirect("/home");
    }

    private void putProductFieldsIntoParameterMap(Product product, Map<String, Object> parameters) {
        parameters.put("id", product.getId());
        parameters.put("name", product.getName());
        parameters.put("image", product.getImage());
        parameters.put("price", product.getPrice());
    }
}
