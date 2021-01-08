package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.RootApplicationContext;
import com.glushkov.shop.entity.Product;
import lombok.val;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig(value = {TestConfiguration.class, RootApplicationContext.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcProductDaoITest {
    @Autowired
    private JdbcProductDao jdbcProductDao;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void init() {
        flyway.migrate();
    }

    @AfterEach
    void afterAll() {
        flyway.clean();
    }

    @Test
    @DisplayName("Returns product from database by id")
    void findByIdTest() {
        //when
        val actualProduct = jdbcProductDao.findById(1);
        //then
        assertEquals(1, actualProduct.getId());
        assertEquals("Victory-1765", actualProduct.getName());
        assertEquals(299.99, actualProduct.getPrice());
        assertEquals("description", actualProduct.getDescription());
        assertEquals("https://i.postimg.cc/J0993gLN/victory-pen-300.jpg", actualProduct.getImage());
    }

    @Test
    @DisplayName("Returns product from database by name")
    void findByNameTest() {
        //when
        val actualList = jdbcProductDao.findByName("Victory-1765");
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
        val actualList = jdbcProductDao.findAll();
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
    void saveTest() {
        //prepare
        assertEquals(5, jdbcProductDao.findAll().size());
        val product = Product.builder()
                .name("Victory-1765")
                .price(299.99)
                .description("description")
                .image("https://i.postimg.cc/J0993gLN/victory-pen-300.jpg").build();
        //when
        jdbcProductDao.save(product);
        val expectedList = jdbcProductDao.findAll();
        //then
        assertFalse(jdbcProductDao.findAll().isEmpty());
        assertEquals(1, expectedList.get(0).getId());
        assertEquals("Victory-1765", expectedList.get(0).getName());
        assertEquals(299.99, expectedList.get(0).getPrice());
        assertEquals("description", expectedList.get(0).getDescription());
        assertEquals("https://i.postimg.cc/J0993gLN/victory-pen-300.jpg", product.getImage());
    }

    @Test
    @DisplayName("Updating product in database")
    void updateTest() {
        //prepare
        val product = Product.builder()
                .id(5)
                .name("Gettysburg-1863")
                .price(129.99)
                .description("description")
                .image("https://i.postimg.cc/y6t4zJZD/carolina-300.jpg").build();

        //when
        jdbcProductDao.update(product);
        val actualProduct = jdbcProductDao.findById(5);
        //then
        assertEquals(5, actualProduct.getId());
        assertEquals("Gettysburg-1863", actualProduct.getName());
        assertEquals(129.99, actualProduct.getPrice());
        assertEquals("description", actualProduct.getDescription());
        assertEquals("https://i.postimg.cc/y6t4zJZD/carolina-300.jpg", product.getImage());
    }

    @Test
    @DisplayName("Deleting user from database")
    void deleteTest() {
        //prepare
        assertEquals(5, jdbcProductDao.findAll().size());
        //when
        jdbcProductDao.delete(5);
        //then
        assertEquals(4, jdbcProductDao.findAll().size());
    }
}
