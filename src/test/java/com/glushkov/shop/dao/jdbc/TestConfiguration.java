package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.RootApplicationContext;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.sql.DataSource;

@SpringJUnitWebConfig(value = {RootApplicationContext.class})
@Configuration
public class TestConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public Flyway configureDataSource() {
        DataSource dataSource = applicationContext.getAutowireCapableBeanFactory().getBean(DataSource.class);
        return Flyway.configure().dataSource(dataSource)
                .locations("classpath:db/migration/products", "classpath:db/migration/users").baselineOnMigrate(true).load();
    }
}
