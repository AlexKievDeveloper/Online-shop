package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.impl.DefaultProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.glushkov.shop.web.WebConstants.CONTENT_TYPE;

public class EditProductServlet extends HttpServlet {
    private DefaultProductService productService = ServiceLocator.getService("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);

        val product = productService.findById(Integer.parseInt(request.getPathInfo().substring(1)));
        val productMap = new HashMap<String, Object>();
        putProductFieldsIntoParameterMap(product, productMap);

        PageGenerator.instance().process("edit", productMap, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val product = Product.builder()
                .id(Integer.parseInt(request.getParameter("id")))
                .name(request.getParameter("name"))
                .description("description")
                .image(request.getParameter("image"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();

        productService.update(product);
        response.sendRedirect("/");
    }

    void putProductFieldsIntoParameterMap(Product product, Map<String, Object> parameters) {
        parameters.put("id", product.getId());
        parameters.put("name", product.getName());
        parameters.put("image", product.getImage());
        parameters.put("price", product.getPrice());
    }
}

