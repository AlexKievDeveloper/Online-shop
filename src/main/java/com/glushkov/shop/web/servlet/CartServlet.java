package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.CartService;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.glushkov.shop.web.WebConstants.CONTENT_TYPE;

public class CartServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.getService("productService");
    private CartService cartService = ServiceLocator.getService("cartService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Product> purchaseList = ((Session) request.getAttribute("session")).getCart();
        val map = new HashMap<String, Object>();
        if (purchaseList != null) {
            map.put("total_cost", cartService.getTotalCost(purchaseList));

            map.put("purchases", purchaseList);
        } else {
            map.put("message", "Sorry, your cart is still empty");
        }
        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("cart", map, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val id = Integer.parseInt(request.getParameter("id"));
        val product = productService.findById(id);

        val session = ((Session) request.getAttribute("session"));
        if (session.getCart() == null) {
            val cart = new ArrayList<Product>();
            cart.add(product);
            session.setCart(cart);
        } else {
            session.getCart().add(product);
        }
        response.sendRedirect("/");
    }
}
