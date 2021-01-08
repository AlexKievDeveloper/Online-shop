package com.glushkov.shop.dao.jdbc;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TestConfiguration {
    @Autowired
    private DataSource dataSource;

    @Bean
    public Flyway configureDataSource() {
        return Flyway.configure().dataSource(dataSource)
                .locations("classpath:db/migration/products").baselineOnMigrate(true).load();
    }
}
