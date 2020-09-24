package com.glushkov.shop.util;

//import com.glushkov.shop.Starter;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.io.InputStream;
import java.net.URI;
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

        @Cleanup InputStream inputStream = getClass().getResourceAsStream(devProperties);
        properties.load(inputStream);
        return properties;
    }

    @SneakyThrows
    Properties readProdProperties() {
        val prodProperties = new Properties();
        val dbUri = new URI(System.getenv("DATABASE_URL"));

        prodProperties.setProperty("db.user", dbUri.getUserInfo().split(":")[0]);
        prodProperties.setProperty("db.password", dbUri.getUserInfo().split(":")[1]);
        prodProperties.setProperty("db.url", "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort()
                + dbUri.getPath() + "?sslmode=require");
        prodProperties.setProperty("port", System.getenv("PORT"));

        return prodProperties;
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
