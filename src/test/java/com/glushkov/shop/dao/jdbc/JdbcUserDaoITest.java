package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.RootApplicationContext;
import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitWebConfig(value = {TestConfiguration.class, RootApplicationContext.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcUserDaoITest {

    @Autowired
    private JdbcUserDao jdbcUserDao;

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
    @DisplayName("Saving user in the db")
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
}
