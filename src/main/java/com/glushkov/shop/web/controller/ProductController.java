package com.glushkov.shop.web.controller;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.entity.Role;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.impl.DefaultProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit/{id}")
    protected String getEditPage(Model model, @PathVariable Integer id) {
        Product product = productService.findById(id);
        putProductFieldsIntoModel(product, model);
        return "edit.html";
    }

    @GetMapping("/view/{id}")
    protected String getProductPage(Model model, @RequestAttribute Session session, @PathVariable Integer id) {
        Role role = session.getUser().getRole();

        if (role != null) {
            Product product = productService.findById(id);
            putProductFieldsIntoModel(product, model);
            model.addAttribute("role", role.getUserRole());
            return "view.html";
        } else {
            return "redirect:/login?message=Access denied. Please login and try again.";
        }
    }

    @GetMapping("/search")
    protected String search(Model model, @RequestParam String enteredName) {

        List<Product> usersList = productService.findByName(enteredName);

        if (usersList.isEmpty()) {
            model.addAttribute("message", "Sorry, no products were found for your request: " + enteredName);
        } else {
            model.addAttribute("products", usersList);
        }

        return "search.html";
    }

    @PostMapping("/add")
    protected String add(@RequestParam String name, @RequestParam String image, @RequestParam Double price) {
        Product product = Product.builder()
                .name(name)
                .description("description")
                .image(image)
                .price(price)
                .build();

        productService.save(product);
        return "redirect:/home";
    }

    @PostMapping("/edit")
    protected String edit(@RequestParam Integer id, @RequestParam String name, @RequestParam String image, @RequestParam Double price) {
        Product product = Product.builder()
                .id(id)
                .name(name)
                .description("description")
                .image(image)
                .price(price)
                .build();

        productService.update(product);
        return "redirect:/home";
    }

    @PostMapping("/delete")
    protected String delete(@RequestParam Integer id) {
        productService.delete(id);
        return "redirect:/home";
    }

    void putProductFieldsIntoModel(Product product, Model model) {
        model.addAttribute("id", product.getId());
        model.addAttribute("name", product.getName());
        model.addAttribute("image", product.getImage());
        model.addAttribute("price", product.getPrice());
    }
}
