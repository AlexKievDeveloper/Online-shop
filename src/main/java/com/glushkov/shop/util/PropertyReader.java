package com.glushkov.shop.util;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@Slf4j
public class PropertyReader {
    private final static String DEFAULT_DEV_PROPERTIES_PATH = "/application.properties";
    private final String[] propertiesPath;

    public PropertyReader() {
        this(DEFAULT_DEV_PROPERTIES_PATH);
    }

    public PropertyReader(String... propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    public Properties getProperties() {
        try {
            val applicationProperties = readApplicationProperties();

            if (("PROD").equals(System.getenv("env"))) {
                val prodProperties = readProdProperties();
                return merge(applicationProperties, prodProperties);
            }
            return applicationProperties;
        } catch (IOException e) {
            log.error("Error while reading properties", e);
            throw new RuntimeException("Error while reading properties", e);
        }
    }

    Properties readApplicationProperties() throws IOException {
        val properties = new Properties();
        for (String s : propertiesPath) {
            try (val inputStream = getClass().getResourceAsStream(s)) {
                properties.load(inputStream);
            }
        }
        return properties;
    }

    Properties readProdProperties() {
        try {
            val prodProperties = new Properties();
            val dbUri = new URI(System.getenv("DATABASE_URL"));

            prodProperties.setProperty("db.user", dbUri.getUserInfo().split(":")[0]);
            prodProperties.setProperty("db.password", dbUri.getUserInfo().split(":")[1]);
            prodProperties.setProperty("db.url", "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort()
                    + dbUri.getPath() + "?sslmode=require");
            prodProperties.setProperty("port", System.getenv("PORT"));

            return prodProperties;
        } catch (URISyntaxException e) {
            log.error("Exception while reading prod properties", e);
            throw new RuntimeException("Exception while reading prod properties", e);
        }
    }

    Properties merge(Properties... properties) {
        val mergedProperties = new Properties();
        for (Properties property : properties) {
            val propertyNames = property.stringPropertyNames();
            for (String name : propertyNames) {
                val propertyValue = property.getProperty(name);
                mergedProperties.setProperty(name, propertyValue);
            }
        }
        return mergedProperties;
    }
}
