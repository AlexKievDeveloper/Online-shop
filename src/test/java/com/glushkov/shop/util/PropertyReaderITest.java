/*
package com.glushkov.shop.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyReaderITest {

    private final PropertyReader propertyReader;
    private final Map<String, String> propertiesMap;

    private PropertyReaderITest() {
        propertyReader = new PropertyReader();
        propertiesMap = new HashMap<>();
    }

    @Test
    @DisplayName("Returns properties from file")
    void getPropertiesTest() {
        //prepare
        propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/online-shop");
        propertiesMap.put("db.user", "postgres");
        propertiesMap.put("db.password", "23061993");
        propertiesMap.put("port", "8080");
        propertiesMap.put("thymeleaf.cache", "false");

        //when
        Properties properties = propertyReader.getProperties();

        //then
        assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
        assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
        assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
        assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
        assertEquals(propertiesMap.get("thymeleaf.cache"), properties.getProperty("thymeleaf.cache"));
    }

    @Test
    @DisplayName("Returns complex properties if environment is PROD")
    void getPropertiesProdEnvironmentTest() throws Exception {
        //prepare
        withEnvironmentVariable("env", "PROD")
                .and("DB.URL", "jdbc:postgresql://localhost:5432/online-shop")
                .and("DB.USER", "postgres")
                .and("DB.PASSWORD", "23061993")
                .and("PORT", "3030")
                .execute(() -> {

                    propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/online-shop");
                    propertiesMap.put("db.user", "postgres");
                    propertiesMap.put("db.password", "23061993");
                    propertiesMap.put("port", "3030");
                    propertiesMap.put("thymeleaf.cache", "false");

                    //when
                    Properties properties = propertyReader.getProperties();

                    //then
                    assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
                    assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
                    assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
                    assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
                    assertEquals(propertiesMap.get("thymeleaf.cache"), properties.getProperty("thymeleaf.cache"));
                });
    }

    @Test
    @DisplayName("Returns properties from application.properties")
    void readApplicationPropertiesTest() {
        //prepare
        propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/online-shop");
        propertiesMap.put("db.user", "postgres");
        propertiesMap.put("db.password", "23061993");
        propertiesMap.put("port", "8080");
        propertiesMap.put("thymeleaf.cache", "false");

        //when
        Properties properties = propertyReader.readApplicationProperties();

        //then
        assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
        assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
        assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
        assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
        assertEquals(propertiesMap.get("thymeleaf.cache"), properties.getProperty("thymeleaf.cache"));
    }

    @Test
    @DisplayName("Returns properties from environment variables")
    void readProdPropertiesTest() throws Exception {
        //prepare
        withEnvironmentVariable("env", "PROD")
                .and("DB.URL", "jdbc:postgresql://localhost:5432/online-shop")
                .and("DB.USER", "postgres")
                .and("DB.PASSWORD", "00000000")
                .and("PORT", "3030")
                .execute(() -> {

                    propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/online-shop");
                    propertiesMap.put("db.user", "postgres");
                    propertiesMap.put("db.password", "00000000");
                    propertiesMap.put("port", "3030");

                    //when
                    Properties prodProperties = propertyReader.readProdProperties();

                    //then
                    assertEquals(propertiesMap.get("db.url"), prodProperties.getProperty("db.url"));
                    assertEquals(propertiesMap.get("db.user"), prodProperties.getProperty("db.user"));
                    assertEquals(propertiesMap.get("db.password"), prodProperties.getProperty("db.password"));
                    assertEquals(propertiesMap.get("port"), prodProperties.getProperty("port"));
                });
    }

    @Test
    @DisplayName("Returns merge properties from application.properties and environment variables")
    void mergeTest() throws Exception {
        //prepare
        withEnvironmentVariable("env", "PROD")
                .and("DB.URL", "jdbc:postgresql://localhost:5432/online-shop")
                .and("DB.USER", "postgres")
                .and("DB.PASSWORD", "00000000")
                .and("PORT", "3030")
                .execute(() -> {

                    propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/online-shop");
                    propertiesMap.put("db.user", "postgres");
                    propertiesMap.put("db.password", "00000000");
                    propertiesMap.put("port", "3030");
                    propertiesMap.put("thymeleaf.cache", "false");

                    Properties applicationProperties = propertyReader.readApplicationProperties();
                    Properties prodProperties = propertyReader.readProdProperties();

                    //when
                    Properties properties = propertyReader.merge(applicationProperties, prodProperties);

                    //then
                    assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
                    assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
                    assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
                    assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
                    assertEquals(propertiesMap.get("thymeleaf.cache"), properties.getProperty("thymeleaf.cache"));
                });
    }
}

*/
