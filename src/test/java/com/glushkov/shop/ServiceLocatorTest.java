package com.glushkov.shop;

import com.glushkov.shop.dao.jdbc.ConnectionManager;
import com.glushkov.shop.dao.jdbc.JdbcProductDao;
import com.glushkov.shop.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.glushkov.shop.ServiceLocator.register;
import static org.junit.jupiter.api.Assertions.*;

class ServiceLocatorTest {

    @Test
    @DisplayName("Registers service")
    void registerTest() throws IOException {
        //prepare
        ConnectionManager connectionManager = new ConnectionManager();
        JdbcProductDao jdbcProductDao = new JdbcProductDao(connectionManager);
        ProductService productService = new ProductService(jdbcProductDao);
        //when
        register("productService", productService);
        //then
        assertEquals(productService, ServiceLocator.getService("productService"));
    }

    @Test
    @DisplayName("Returns service")
    void getServiceTest() {
        assertNotNull(ServiceLocator.getService("productService"));
    }
}