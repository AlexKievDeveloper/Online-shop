package com.glushkov.shop.reader;

import com.glushkov.shop.Starter;

import java.io.IOException;
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
        Properties applicationProperties = readApplicationProperties();

        if (("PROD").equals(System.getenv("env"))) {
            Properties prodProperties = readProdProperties();
            return merge(applicationProperties, prodProperties);
        }

        return applicationProperties;
    }

    Properties readApplicationProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = Starter.class.getResourceAsStream(devProperties)) {
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading " + devProperties, e);
        }
    }

    Properties readProdProperties() {
        Properties prodProperties = new Properties();
        prodProperties.setProperty("db-prod.url", System.getenv("db.url"));
        prodProperties.setProperty("db-prod.user", System.getenv("db.user"));
        prodProperties.setProperty("db-prod.password", System.getenv("db.password"));
        prodProperties.setProperty("port-prod", System.getenv("PORT"));
        return prodProperties;
    }

    Properties merge(Properties applicationProperties, Properties prodProperties) {
        applicationProperties.setProperty("db.url", prodProperties.getProperty("db-prod.url"));
        applicationProperties.setProperty("db.user", prodProperties.getProperty("db-prod.user"));
        applicationProperties.setProperty("db.password", prodProperties.getProperty("db-prod.password"));
        applicationProperties.setProperty("port", prodProperties.getProperty("port-prod"));
        return applicationProperties;
    }
}
