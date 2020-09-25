package com.glushkov.shop;

import com.glushkov.shop.dao.jdbc.JdbcProductDao;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.util.PropertyReader;
import lombok.val;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<String, Object> SERVICES = new HashMap<>();

    static {
        val propertyReader = new PropertyReader();
        val properties = propertyReader.getProperties();

        val pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setURL(properties.getProperty("db.url"));
        pgSimpleDataSource.setUser(properties.getProperty("db.user"));
        pgSimpleDataSource.setPassword(properties.getProperty("db.password"));

        val jdbcProductDao = new JdbcProductDao(pgSimpleDataSource);
        val productService = new ProductService(jdbcProductDao);
        register("productService", productService);
    }

    public static void register(String serviceName, Object service) {
        SERVICES.put(serviceName, service);
    }

    public static <T> T getService(String serviceName) {
        return (T)SERVICES.get(serviceName);
    }
}
