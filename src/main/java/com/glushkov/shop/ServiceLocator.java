package com.glushkov.shop;

import com.glushkov.shop.dao.jdbc.ConnectionManager;
import com.glushkov.shop.dao.jdbc.JdbcProductDao;
import com.glushkov.shop.service.ProductService;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<String, Object> SERVICES = new HashMap<>();

    static {
        ConnectionManager connectionManager = new ConnectionManager();
        JdbcProductDao jdbcProductDao = new JdbcProductDao(connectionManager);
        ProductService productService = new ProductService(jdbcProductDao);
        register("productService", productService);
    }

    public static void register(String serviceName, Object service) {
        SERVICES.put(serviceName, service);
    }

    public static Object getService(String serviceName) {
        return SERVICES.get(serviceName);
    }
}
