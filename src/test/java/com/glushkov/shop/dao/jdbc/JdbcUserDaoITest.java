
/*package com.glushkov.shop.dao.jdbc;

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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("Returns null when the user is not in the db")
    void saveTest() {
        //prepare
        User user = User.builder()
                .login("Dima")
                .password("1234")
                .role(Role.USER)
                .sole("12vgsdg1vs4t3")
                .build();
        //when
        jdbcUserDao.save(user);
        //then
        assertTrue(jdbcUserDao.isLoginExist("Dima"));
    }

    @Test
    @DisplayName("Returns true if login exist and false if not")
    void isLoginExistTest() {
        //when
        boolean actual = jdbcUserDao.isLoginExist("Alex");
        //then
        assertTrue(actual);
    }

    @Test
    @DisplayName("Returns true if login exist and false if not")
    void isLoginExistFalseExpectedTest() {
        //when
        boolean actual = jdbcUserDao.isLoginExist("AlexX");
        //then
        assertFalse(actual);
    }
}*/
