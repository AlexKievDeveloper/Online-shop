package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.dao.UserDao;
import com.glushkov.shop.dao.jdbc.mapper.UserRowMapper;
import com.glushkov.shop.entity.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class JdbcUserDao implements UserDao {
    private static final String FIND_USER = "SELECT id, login, password, role FROM users WHERE login = ? AND password = ?";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findUser(String login, String password) {

        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(FIND_USER)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (!resultSet.next()) {
                    log.info("No user found for login: {} and password: {}", login, password);
                    return null;
                }

                val user = USER_ROW_MAPPER.mapRow(resultSet);

                if (resultSet.next()) {
                    log.error("More than one found for login: {}", login);
                    throw new RuntimeException("More than one user found for login: ".concat(login));
                }
                return user;
            }
        } catch (SQLException e) {
            log.error("Exception while getting user from DB: {}", login, e);
            throw new RuntimeException("Exception while getting user from DB: ".concat(login), e);
        }
    }
}


