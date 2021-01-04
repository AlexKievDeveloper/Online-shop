package com.glushkov.shop.web.listener;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.security.DefaultSecurityService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThymeleafInitializerListener implements ServletContextListener {
    private DefaultSecurityService securityService = ServiceLocator.getService("securityService");

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("Initializing thymeleaf processor");
        ServletContext servletContext = event.getServletContext();
        PageGenerator.instance().configTemplate(servletContext);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(securityService, 0, 10, TimeUnit.MINUTES);
    }
}
