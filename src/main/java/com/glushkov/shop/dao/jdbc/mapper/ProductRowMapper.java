package com.glushkov.shop.dao.jdbc.mapper;

import com.glushkov.shop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {

        return Product.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getDouble("price"))
                .description(resultSet.getString("description"))
                .image(resultSet.getString("image")).build();
    }
}
