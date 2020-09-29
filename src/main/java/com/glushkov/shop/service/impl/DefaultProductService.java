package com.glushkov.shop.service.impl;

import com.glushkov.shop.dao.ProductDao;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.service.ProductService;

import java.util.List;

public class DefaultProductService implements ProductService {

    private final ProductDao productDao;

    public DefaultProductService(ProductDao productDao) {
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
