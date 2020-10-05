package com.glushkov.shop.service;

import com.glushkov.shop.entity.Product;

import java.util.List;

public interface CartService {
    double getTotalCost(List<Product> purchaseList);
}
