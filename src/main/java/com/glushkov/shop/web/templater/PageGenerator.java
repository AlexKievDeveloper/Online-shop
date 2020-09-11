package com.glushkov.shop.web.templater;

import com.glushkov.shop.util.PropertyReader;
import lombok.val;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Map;

public class PageGenerator {
    private static PageGenerator pageGenerator;
    private final TemplateEngine templateEngine;

    public static PageGenerator instance() throws URISyntaxException {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public void process(String template, Map<String, Object> paramsMap, Writer writer) {
        val context = new Context();
        context.setVariables(paramsMap);

        templateEngine.process(template, context, writer);
    }

    public void process(String template, Writer writer) {
        val context = new Context();
        templateEngine.process(template, context, writer);
    }

    public PageGenerator() throws URISyntaxException {
        templateEngine = new TemplateEngine();
        val templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("webapp/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");

        val propertyReader = new PropertyReader();
        val properties = propertyReader.getProperties();
        templateResolver.setCacheable(Boolean.parseBoolean(properties.getProperty("thymeleaf.cache")));

        templateEngine.setTemplateResolver(templateResolver);
    }
}
