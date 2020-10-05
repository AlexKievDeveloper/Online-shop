package com.glushkov.shop.service.impl;

import com.glushkov.shop.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultCartServiceTest {
    @Test
    @DisplayName("Returns total products cost")
    void getTotalCost() {
        //prepare
        Product firstProduct = Product.builder()
                .price(299)
                .build();
        Product secondProduct = Product.builder()
                .price(101)
                .build();
        DefaultCartService defaultCartService = new DefaultCartService();
        List<Product> productList = new ArrayList<>();
        productList.add(firstProduct);
        productList.add(secondProduct);
        //when
        double actual = defaultCartService.getTotalCost(productList);
        //then
        assertEquals(400, actual);
    }
}