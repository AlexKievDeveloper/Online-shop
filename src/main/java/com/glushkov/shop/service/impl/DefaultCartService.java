package com.glushkov.shop.service.impl;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultCartService implements CartService {

    @Override
    public double getTotalCost(List<Product> purchaseList) {
        double totalCost = 0;
        for (Product product : purchaseList) {
            totalCost = totalCost + product.getPrice();
        }
        return totalCost;
    }
}
