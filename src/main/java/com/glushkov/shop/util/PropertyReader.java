package com.glushkov.shop.util;

import com.glushkov.shop.Starter;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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

    public Properties getProperties() throws URISyntaxException {
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

    Properties readProdProperties() throws URISyntaxException {
        val prodProperties = new Properties();
        val dbUri = new URI(System.getenv("DATABASE_URL"));

        prodProperties.setProperty("db.user", dbUri.getUserInfo().split(":")[0]);
        prodProperties.setProperty("db.password", dbUri.getUserInfo().split(":")[1]);
        prodProperties.setProperty("db.url", "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort()
                + dbUri.getPath() + "?sslmode=require");
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
