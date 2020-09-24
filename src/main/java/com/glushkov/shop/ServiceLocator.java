package com.glushkov.shop;

import com.glushkov.shop.dao.jdbc.ConnectionManager;
import com.glushkov.shop.dao.jdbc.JdbcProductDao;
import com.glushkov.shop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ServiceLocator {
    private static final Map<String, Object> SERVICES = new HashMap<>();

    static {
        try {
            val connectionManager = new ConnectionManager();
            val jdbcProductDao = new JdbcProductDao(connectionManager);
            val productService = new ProductService(jdbcProductDao);
            register("productService", productService);
        } catch (IOException e) {
            log.error("Error while connection manager creation", e);
            throw new RuntimeException("Error while connection manager creation", e);
        }
    }

    public static void register(String serviceName, Object service) {
        SERVICES.put(serviceName, service);
    }

    public static Object getService(String serviceName) {
        return SERVICES.get(serviceName);
    }
}
