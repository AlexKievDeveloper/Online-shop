package com.glushkov.shop.dao;

import com.glushkov.shop.entity.Product;

import java.util.List;

public interface ProductDao {

    Product findById(int id);

    List<Product> findByName(String name);

    List<Product> findAll();

    void save(Product product);

    void update(Product product);

    void delete(int productId);
}
