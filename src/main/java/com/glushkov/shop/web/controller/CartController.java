package com.glushkov.shop.web.controller;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.CartService;
import com.glushkov.shop.service.impl.DefaultProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final DefaultProductService productService;
    private final CartService cartService ;

    @GetMapping
    protected String getCartPage(Model model, HttpServletRequest request) {

        List<Product> purchaseList = ((Session) request.getAttribute("session")).getCart();

        if (purchaseList != null) {
            model.addAttribute("total_cost", cartService.getTotalCost(purchaseList));
            model.addAttribute("purchases", purchaseList);
        } else {
            model.addAttribute("message", "Sorry, your cart is still empty");
        }

        return "cart.html";
    }

    @PostMapping
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val id = Integer.parseInt(request.getParameter("id"));
        val product = productService.findById(id);

        val session = ((Session) request.getAttribute("session"));
        if (session.getCart() == null) {
            List<Product> cart = new ArrayList<>();
            cart.add(product);
            session.setCart(cart);
        } else {
            session.getCart().add(product);
        }
        response.sendRedirect("/home");
    }
}
