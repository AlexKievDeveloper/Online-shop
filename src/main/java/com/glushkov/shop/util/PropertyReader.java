package com.glushkov.shop.util;

import com.glushkov.shop.Starter;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private final static String DEFAULT_DEV_PROPERTIES_PATH = "/application.properties";

    private final String devProperties;

    public PropertyReader() {
        this(DEFAULT_DEV_PROPERTIES_PATH);
    }

    public PropertyReader(String devProperties) {
        this.devProperties = devProperties;
    }

    public Properties getProperties() {
        val applicationProperties = readApplicationProperties();

        if (("PROD").equals(System.getenv("env"))) {
            val prodProperties = readProdProperties();
            return merge(applicationProperties, prodProperties);
        }

        return applicationProperties;
    }

    @SneakyThrows
    Properties readApplicationProperties() {
        val properties = new Properties();

        @Cleanup InputStream inputStream = Starter.class.getResourceAsStream(devProperties);
        properties.load(inputStream);
        return properties;
    }

    Properties readProdProperties() {
        val prodProperties = new Properties();
        prodProperties.setProperty("db.url", System.getenv("DB.URL"));
        prodProperties.setProperty("db.user", System.getenv("DB.USER"));
        prodProperties.setProperty("db.password", System.getenv("DB.PASSWORD"));
        prodProperties.setProperty("port", System.getenv("PORT"));
        return prodProperties;
    }

    Properties merge(Properties applicationProperties, Properties prodProperties) {
        applicationProperties.setProperty("db.url", prodProperties.getProperty("db.url"));
        applicationProperties.setProperty("db.user", prodProperties.getProperty("db.user"));
        applicationProperties.setProperty("db.password", prodProperties.getProperty("db.password"));
        applicationProperties.setProperty("port", prodProperties.getProperty("port"));
        return applicationProperties;
    }
}
