package com.glushkov.shop.dao.jdbc.mapper;

import com.glushkov.shop.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {
    @Test
    @DisplayName("Returns an object of class User from result set")
    void productRowMapperTest() throws SQLException {
        //prepare
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Victory-1765");
        when(mockResultSet.getDouble("price")).thenReturn(299.99);
        when(mockResultSet.getString("description")).thenReturn("description");
        when(mockResultSet.getString("image")).thenReturn("https://i.postimg.cc/J0993gLN/victory-pen-300.jpg");

        ProductRowMapper productRowMapper = new ProductRowMapper();

        //when
        Product actual = productRowMapper.mapRow(mockResultSet);

        //then
        assertNotNull(actual);
        assertEquals(1, actual.getId());
        assertEquals("Victory-1765", actual.getName());
        assertEquals(299.99, actual.getPrice());
        assertEquals("description", actual.getDescription());
        assertEquals("https://i.postimg.cc/J0993gLN/victory-pen-300.jpg", actual.getImage());
    }
}