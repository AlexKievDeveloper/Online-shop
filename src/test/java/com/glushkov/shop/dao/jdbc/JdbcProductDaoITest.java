/*
package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.reader.PropertyReader;
import com.glushkov.shop.entity.Product;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class JdbcProductDaoITest {

    private final JdbcDataSource dataSource;
    private final JdbcProductDao jdbcProductDao;

    JdbcProductDaoITest() throws SQLException {
        PropertyReader propertyReader = new PropertyReader("/application-test.properties");
        Properties properties = propertyReader.getProperties();

        dataSource = new JdbcDataSource();
        dataSource.setURL(properties.getProperty("jdbc.host"));
        dataSource.setUser(properties.getProperty("jdbc.user"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        dataSource.getConnection();

        dataSource.setURL(properties.getProperty("jdbc.url"));

        jdbcProductDao = new JdbcProductDao(dataSource);
    }

    @Test
    @DisplayName("Returns product from database by id")
    void findByIdTest() throws IOException {
        //prepare
        byte[] image = getClass().getResourceAsStream("/oil.jpg").readAllBytes();

        //when
        Product actualProduct = jdbcProductDao.findById(1);

        //then
        assertEquals(1, actualProduct.getId());
        assertEquals("Oil", actualProduct.getName());
        assertEquals(46.40, actualProduct.getPrice());
        assertEquals("Brent Crude Oil", actualProduct.getDescription());
        for (int i = 0; i < image.length; i++) {
            assertEquals(image[i], actualProduct.getImage()[i]);
        }
    }

    @Test
    @DisplayName("Returns product from database by name")
    void findByNameTest() {
        //when
        List<Product> actualList = jdbcProductDao.findByName("Oil");

        //then
        assertFalse(actualList.isEmpty());
        assertEquals(5, actualList.size());
        for (Product product : actualList) {
            assertNotEquals(0, product.getId());
            assertNotNull(product.getName());
            assertNotEquals(0, product.getPrice());
            assertNotNull(product.getDescription());
            assertNotNull(product.getImage());
        }
    }

    @Test
    @DisplayName("Returns all products from database")
    void findAllTest() {
        //when
        List<Product> actualList = jdbcProductDao.findAll();

        //then
        assertFalse(actualList.isEmpty());
        assertEquals(5, actualList.size());
        for (Product product : actualList) {
            assertNotEquals(0, product.getId());
            assertNotNull(product.getName());
            assertNotEquals(0, product.getPrice());
            assertNotNull(product.getDescription());
            assertNotNull(product.getImage());
        }
    }

    @Test
    @DisplayName("Saving product to database")
    void saveTest() throws IOException {
        //prepare
        byte[] image = getClass().getResourceAsStream("/oil.jpg").readAllBytes();
        assertEquals(5, jdbcProductDao.findAll().size());
        Product product = new Product();
        product.setName("Oil");
        product.setPrice(46.40);
        product.setDescription("Brent Crude Oil");
        product.setImage(image);

        //when
        jdbcProductDao.save(product);
        List<Product> expectedList = jdbcProductDao.findAll();

        //then
        assertFalse(jdbcProductDao.findAll().isEmpty());
        assertEquals(1, expectedList.get(0).getId());
        assertEquals("Oil", expectedList.get(0).getName());
        assertEquals(46.40, expectedList.get(0).getPrice());
        assertEquals("Brent Crude Oil", expectedList.get(0).getDescription());
        for (int i = 0; i < image.length; i++) {
            assertEquals(image[i], expectedList.get(0).getImage()[i]);
        }
    }
    
    @Test
    @DisplayName("Updating product in database")
    void updateTest() throws IOException {
        //prepare
        byte[] image = getClass().getResourceAsStream("/oil.jpg").readAllBytes();
        Product product = new Product();
        product.setId(5);
        product.setName("Gas");
        product.setPrice(2.57);
        product.setDescription("Blue fuel");
        product.setImage(image);

        //when
        jdbcProductDao.update(product);
        Product actualProduct = jdbcProductDao.findById(5);

        //then
        assertEquals(5, actualProduct.getId());
        assertEquals("Gas", actualProduct.getName());
        assertEquals(2.57, actualProduct.getPrice());
        assertEquals("Blue fuel", actualProduct.getDescription());

        for (int i = 0; i < image.length; i++) {
            assertEquals(image[i], actualProduct.getImage()[i]);
        }
    }

    @Test
    @DisplayName("Deleting user from database")
    void deleteTest() {
        //prepare
        assertEquals(5, jdbcProductDao.findAll().size());

        //when
        jdbcProductDao.delete(5);

        // then
        assertEquals(4, jdbcProductDao.findAll().size());
    }

    @AfterEach
    void cleanUp() throws SQLException {
        String dropTable = "DROP TABLE products";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(dropTable);
        }
    }
}*/
