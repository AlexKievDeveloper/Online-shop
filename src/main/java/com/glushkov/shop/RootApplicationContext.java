package com.glushkov.shop;

import com.glushkov.shop.security.DefaultSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@ComponentScan(value = "com.glushkov.shop", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "com.glushkov.shop.web.controller.*"))
@PropertySource("classpath:application.properties")
public class RootApplicationContext {
    @Autowired
    private DefaultSecurityService defaultSecurityService;

    @Bean
    protected DataSource dataSource(@Value("${jdbc.url}") String url,
                                    @Value("${jdbc.user}") String userName,
                                    @Value("${jdbc.password}") String password) {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    protected ScheduledExecutorService getExecutorService() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(defaultSecurityService, 0, 10, TimeUnit.MINUTES);
        return executorService;
    }

}

