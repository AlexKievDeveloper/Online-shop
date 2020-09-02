package com.glushkov.shop.service;

import com.glushkov.shop.dao.ProductDao;
import com.glushkov.shop.entity.Product;

import java.util.List;

public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product findById(int id) {
        return productDao.findById(id);
    }

    public List<Product> findByName(String name) {
        return productDao.findByName(name);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void delete(int id) {
        productDao.delete(id);
    }
}
