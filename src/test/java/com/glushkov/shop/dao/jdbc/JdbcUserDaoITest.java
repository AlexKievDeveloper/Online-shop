package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.util.PropertyReader;
import lombok.val;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDaoITest {
    private JdbcUserDao jdbcUserDao;
    private Flyway flyway;

    public JdbcUserDaoITest() {
        val propertyReader = new PropertyReader("/application-test.properties");
        val properties = propertyReader.getProperties();

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(properties.getProperty("jdbc.default-url"));

        flyway = Flyway.configure().dataSource(dataSource).locations("db/migration/users").load();
        jdbcUserDao = new JdbcUserDao(dataSource);
    }

    @BeforeEach
    void init() {
        flyway.migrate();
    }

    @AfterEach
    void afterAll() {
        flyway.clean();
    }

    @Test
    @DisplayName("Returns a user from db")
    void findUserTest() {
        //when
        User actual = jdbcUserDao.findUser("Alex", "11111111");
        //then
        assertNotNull(actual);
        assertEquals(1, actual.getId());
        assertEquals("Alex", actual.getLogin());
        assertEquals("11111111", actual.getPassword());
        assertEquals(Role.ADMIN, actual.getRole());
    }

    @Test
    @DisplayName("Returns null when the user is not in the db")
    void findUserExpectedNullTest() {
        //when
        User actual = jdbcUserDao.findUser("Alex1", "11111111");
        //then
        assertNull(actual);
    }
}