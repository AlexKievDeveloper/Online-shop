package com.glushkov.shop.dao.jdbc.mapper;

import com.glushkov.shop.entity.Role;
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
class UserRowMapperTest {
    @Mock
    private ResultSet mockResultSet;

    @Test
    @DisplayName("Returns an object of class User from result set")
    void mapRowTest() throws SQLException {
        //prepare
        UserRowMapper userRowMapper = new UserRowMapper();
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("login")).thenReturn("User");
        when(mockResultSet.getString("password")).thenReturn("1111");
        when(mockResultSet.getString("role")).thenReturn("USER");
        //when
        val actual = userRowMapper.mapRow(mockResultSet);
        //then
        assertNotNull(actual);
        assertEquals(1, actual.getId());
        assertEquals("User", actual.getLogin());
        assertEquals("1111", actual.getPassword());
        assertEquals(Role.USER, actual.getRole());
    }
}