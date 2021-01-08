package com.glushkov.shop.web.controller;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.impl.DefaultProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final DefaultProductService productService;

    @GetMapping("/home")
    protected String getHomePage(Model model) {
        log.info("Got request for home page");
        List<Product> productList = productService.findAll();
        model.addAttribute("products", productList);
        return "home.html";
    }

    @GetMapping("/add")
    protected String getAddPage() {
        return "add-product.html";
    }

    @GetMapping("/edit/*")
    protected String getEditPage(Model model, HttpServletRequest request) {
        val product = productService.findById(Integer.parseInt(request.getPathInfo().substring(1)));
        putProductFieldsIntoModel(product, model);
        return "edit.html";
    }

    @GetMapping("/view/*")
    protected String getProductPage(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        val role = ((Session) request.getAttribute("session")).getUser().getRole();
        val product = productService.findById(Integer.parseInt(request.getPathInfo().substring(6)));
        putProductFieldsIntoModel(product, model);

        if (role != null) {
            model.addAttribute("role", role.getUserRole());
            return "view.html";
        } else {
            response.sendRedirect("/login?message=Access denied. Please login and try again.");
            model.addAttribute("message", "Access denied. Please login and try again.");
            return "login.html";
        }
    }

    @GetMapping("/search")
    protected String search(Model model, HttpServletRequest request) {

        val enteredName = request.getParameter("enteredName");
        val usersList = productService.findByName(enteredName);

        if (usersList.isEmpty()) {
            model.addAttribute("message", "Sorry, no products were found for your request: " + enteredName);
        } else {
            model.addAttribute("products", usersList);
        }

        return "search.html";
    }

    @PostMapping("/add")
    protected void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val product = Product.builder()
                .name(request.getParameter("name"))
                .description("description")
                .image(request.getParameter("image"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();

        productService.save(product);
        response.sendRedirect("/home");
    }

    @PostMapping("/edit/*")
    protected void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    @DeleteMapping("/delete")
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);
        response.sendRedirect("/home");
    }

    void putProductFieldsIntoModel(Product product, Model model) {
        model.addAttribute("id", product.getId());
        model.addAttribute("name", product.getName());
        model.addAttribute("image", product.getImage());
        model.addAttribute("price", product.getPrice());
    }
}
