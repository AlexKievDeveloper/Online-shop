package com.glushkov.shop.web.servlet;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
public class AddProductServlet extends HttpServlet {

    private final ProductService productService;

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();

        PageGenerator pageGenerator = PageGenerator.instance();
        pageGenerator.process("/add-product", map, response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setPrice(Double.parseDouble(request.getParameter("price")));
        product.setDescription("description");
        Part part = request.getPart("image");
        String clientsFileName = part.getSubmittedFileName();
        product.setImage("/img/".concat(clientsFileName));

        File file = new File("src/main/resources/webapp/static/img/".concat(clientsFileName));

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
             InputStream inputStream = part.getInputStream()) {

            byte[] buffer = new byte[8192];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, count);
            }
        }

        productService.save(product);
        response.sendRedirect("/home");
    }
}
