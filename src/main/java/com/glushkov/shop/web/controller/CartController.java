package com.glushkov.shop.web.controller;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.CartService;
import com.glushkov.shop.service.impl.DefaultProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final DefaultProductService productService;
    private final CartService cartService;

    @GetMapping
    protected String getCartPage(Model model, @RequestAttribute Session session) {

        List<Product> purchaseList = session.getCart();

        if (purchaseList != null) {
            model.addAttribute("total_cost", cartService.getTotalCost(purchaseList));
            model.addAttribute("purchases", purchaseList);
        } else {
            model.addAttribute("message", "Sorry, your cart is still empty");
        }

        return "cart.html";
    }

    @PostMapping
    protected String doPost(@RequestParam Integer id, @RequestAttribute Session session) {

        Product product = productService.findById(id);

        if (session.getCart() == null) {
            List<Product> cart = new ArrayList<>();
            cart.add(product);
            session.setCart(cart);
        } else {
            session.getCart().add(product);
        }
        return "redirect:/home";
    }
}
