package com.glushkov.shop;

import com.glushkov.shop.dao.jdbc.JdbcProductDao;
import com.glushkov.shop.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.glushkov.shop.ServiceLocator.register;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ServiceLocatorTest {
    @Mock
    JdbcProductDao jdbcProductDao;

    @Test
    @DisplayName("Registers service")
    void registerTest() {
        //prepare
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