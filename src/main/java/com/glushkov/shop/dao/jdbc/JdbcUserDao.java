package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.dao.UserDao;
import com.glushkov.shop.dao.jdbc.mapper.UserRowMapper;
import com.glushkov.shop.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {

    @Autowired
    private String findUserByLogin;
    @Autowired
    private String saveUser;
    @Autowired
    private String isExist;

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private final DataSource dataSource;

    @Override
    public User findUserByLogin(String login) {//TODO test!
        log.info("UserDao find user by login");
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(findUserByLogin)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                log.info("UserDao got result set for find user by login");
                if (!resultSet.next()) {
                    log.info("No user found for login: {}", login);
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

    public void save(User user) {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(saveUser)) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().getUserRole());
            preparedStatement.setString(4, user.getSole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while connection to DB, method save(user)", e);
            throw new RuntimeException("Error while connection to DB, method save(user)", e);
        }
    }

    public boolean isLoginExist(String login) {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(isExist)) {
            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            log.error("Error while connection to DB, method isLoginExist()", e);
            throw new RuntimeException("Error while connection to DB, method isLoginExist()", e);
        }
    }
}


