package com.glushkov.shop.dao.jdbc.mapper;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductRowMapperTest {
    @Mock
    private ResultSet mockResultSet;

    @Test
    @DisplayName("Returns an object of class Product from result set")
    void productRowMapperTest() throws SQLException {
        //prepare
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Victory-1765");
        when(mockResultSet.getDouble("price")).thenReturn(299.99);
        when(mockResultSet.getString("description")).thenReturn("description");
        when(mockResultSet.getString("image")).thenReturn("https://i.postimg.cc/J0993gLN/victory-pen-300.jpg");
        val productRowMapper = new ProductRowMapper();
        //when
        val actual = productRowMapper.mapRow(mockResultSet);
        //then
        assertNotNull(actual);
        assertEquals(1, actual.getId());
        assertEquals("Victory-1765", actual.getName());
        assertEquals(299.99, actual.getPrice());
        assertEquals("description", actual.getDescription());
        assertEquals("https://i.postimg.cc/J0993gLN/victory-pen-300.jpg", actual.getImage());
    }
}