package com.glushkov.shop.dao.jdbc.mapper;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {

        return User.builder()
                .id(resultSet.getInt("id"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .role(Role.valueOf(resultSet.getString("role")))
                .sole(resultSet.getString("sole"))
                .build();
    }
}

