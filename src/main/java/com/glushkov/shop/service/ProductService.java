package com.glushkov.shop.service;

import com.glushkov.shop.entity.Product;

import java.util.List;

public interface ProductService {

    Product findById(int id);

    List<Product> findByName(String name);

    List<Product> findAll();

    void save(Product product);

    void update(Product product);

    void delete(int id);
}
