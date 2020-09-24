package com.glushkov.shop.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConnectionManagerTest {

    @Test
    @DisplayName("Returns a database connection")
    void getConnectionTest() throws IOException {
        //prepare
        ConnectionManager connectionManager = new ConnectionManager();
        //when
        Connection connection = connectionManager.getConnection();
        //then
        assertNotNull(connection);
    }
}