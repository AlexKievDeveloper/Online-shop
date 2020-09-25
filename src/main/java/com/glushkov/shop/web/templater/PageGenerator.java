package com.glushkov.shop.web.templater;

import com.glushkov.shop.util.PropertyReader;
import lombok.val;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.io.Writer;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class PageGenerator {
    private static final TemplateEngine TEMPLATE_ENGINE = new TemplateEngine();
    private static boolean isConfigured;

    public synchronized static void configTemplate(ServletContext servletContext) {
        if (isConfigured) {
            return;
        }
        isConfigured = true;
        val templateResolver = new ServletContextTemplateResolver(servletContext);
        val propertyReader = new PropertyReader();

        Properties properties = propertyReader.getProperties();
        templateResolver.setPrefix(properties.getProperty("thymeleaf.prefix"));
        templateResolver.setSuffix(properties.getProperty("thymeleaf.suffix"));
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(Boolean.parseBoolean(properties.getProperty("thymeleaf.cache")));
        TEMPLATE_ENGINE.setTemplateResolver(templateResolver);
    }

    public static void process(String template, Map<String, Object> productMap, Writer writer) {
        val context = new Context(Locale.getDefault(), productMap);
        TEMPLATE_ENGINE.process(template, context, writer);
    }

    public static void process(String template, Writer writer) {
        process(template, Collections.emptyMap(), writer);
    }
}
