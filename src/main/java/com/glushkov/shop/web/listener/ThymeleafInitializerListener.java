package com.glushkov.shop.web.listener;

import com.glushkov.shop.web.templater.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class ThymeleafInitializerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("Initializing thymeleaf processor");
        ServletContext servletContext = event.getServletContext();
        PageGenerator.configTemplate(servletContext);
    }

    @Override //TODO как спрятать?
    public void contextDestroyed(ServletContextEvent event) {

    }
}
