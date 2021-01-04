package com.glushkov.shop.util;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        val properties = propertyReader.getProperties();

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
                .and("DATABASE_URL", "postgres://fxycdcgratlvtp:20c788b34de130ce61094c1d00816cbc421949ab1bf" +
                        "37260b599a01104067ca2@ec2-52-87-22-151.compute-1.amazonaws.com:5432/d79t7njj3vpo8n")
                .and("PORT", "3030")
                .execute(this::testProdProperties);
    }

    void testProdProperties() {
        propertiesMap.put("db.url", "jdbc:postgresql://ec2-52-87-22-151.compute-1.amazonaws.com:5432/d79t7n" +
                "jj3vpo8n?sslmode=require");
        propertiesMap.put("db.user", "fxycdcgratlvtp");
        propertiesMap.put("db.password", "20c788b34de130ce61094c1d00816cbc421949ab1bf37260b599a01104067ca2");
        propertiesMap.put("port", "3030");
        propertiesMap.put("thymeleaf.cache", "false");

        //when
        val properties = propertyReader.getProperties();

        //then
        assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
        assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
        assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
        assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
        assertEquals(propertiesMap.get("thymeleaf.cache"), properties.getProperty("thymeleaf.cache"));
    }

    @Test
    @DisplayName("Returns properties from application.properties")
    void readApplicationPropertiesTest() throws IOException {
        //prepare
        propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/online-shop");
        propertiesMap.put("db.user", "postgres");
        propertiesMap.put("db.password", "23061993");
        propertiesMap.put("port", "8080");
        propertiesMap.put("thymeleaf.cache", "false");

        //when
        val properties = propertyReader.readApplicationProperties();

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
                .and("DATABASE_URL", "postgres://fxycdcgratlvtp:20c788b34de130ce61094c1d00816cbc421949ab1bf" +
                        "37260b599a01104067ca2@ec2-52-87-22-151.compute-1.amazonaws.com:5432/d79t7njj3vpo8n")
                .and("PORT", "3030")
                .execute(this::testReadingProdProperties);
    }

    void testReadingProdProperties() {
        propertiesMap.put("db.url", "jdbc:postgresql://ec2-52-87-22-151.compute-1.amazonaws.com:5432/d79t7n" +
                "jj3vpo8n?sslmode=require");
        propertiesMap.put("db.user", "fxycdcgratlvtp");
        propertiesMap.put("db.password", "20c788b34de130ce61094c1d00816cbc421949ab1bf37260b599a01104067ca2");
        propertiesMap.put("port", "3030");

        //when
        val prodProperties = propertyReader.readProdProperties();

        //then
        assertEquals(propertiesMap.get("db.url"), prodProperties.getProperty("db.url"));
        assertEquals(propertiesMap.get("db.user"), prodProperties.getProperty("db.user"));
        assertEquals(propertiesMap.get("db.password"), prodProperties.getProperty("db.password"));
        assertEquals(propertiesMap.get("port"), prodProperties.getProperty("port"));
    }

    @Test
    @DisplayName("Returns merge properties from application.properties and environment variables")
    void mergeTest() throws Exception {
        //prepare
        withEnvironmentVariable("env", "PROD")
                .and("DATABASE_URL", "postgres://fxycdcgratlvtp:20c788b34de130ce61094c1d00816cbc421949ab1bf" +
                        "37260b599a01104067ca2@ec2-52-87-22-151.compute-1.amazonaws.com:5432/d79t7njj3vpo8n")
                .and("PORT", "3030")
                .execute(this::testMergeProperties);
    }

    void testMergeProperties() throws IOException {
        propertiesMap.put("db.url", "jdbc:postgresql://ec2-52-87-22-151.compute-1.amazonaws.com:5432/d79t7n" +
                "jj3vpo8n?sslmode=require");
        propertiesMap.put("db.user", "fxycdcgratlvtp");
        propertiesMap.put("db.password", "20c788b34de130ce61094c1d00816cbc421949ab1bf37260b599a01104067ca2");
        propertiesMap.put("port", "3030");
        propertiesMap.put("thymeleaf.cache", "false");

        val applicationProperties = propertyReader.readApplicationProperties();
        val prodProperties = propertyReader.readProdProperties();

        //when
        val properties = propertyReader.merge(applicationProperties, prodProperties);

        //then
        assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
        assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
        assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
        assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
        assertEquals(propertiesMap.get("thymeleaf.cache"), properties.getProperty("thymeleaf.cache"));
    }
}


