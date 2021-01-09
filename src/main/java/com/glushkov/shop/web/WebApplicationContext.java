package com.glushkov.shop.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebMvc
@ComponentScan({"com.glushkov.shop.web.controller"})
public class WebApplicationContext implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    private String thymeleafPrefix = "WEB-INF/templates/";
    private String thymeleafCache = "false";
    private String thymeleafContentType = "text/html";
    private String thymeleafCharacterEncoding = "UTF-8";

    @Bean
    @Description("Thymeleaf Template Resolver")
    public ITemplateResolver htmlTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix(thymeleafPrefix);
        resolver.setCacheable(Boolean.parseBoolean(thymeleafCache));
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    @Bean
    @Description("Thymeleaf Template Engine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(messageSource());
        return templateEngine;
    }

    @Bean
    @Description("Thymeleaf View Resolver")
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setContentType(thymeleafContentType);
        viewResolver.setCharacterEncoding(thymeleafCharacterEncoding);
        return viewResolver;
    }

    @Bean
    @Description("Spring Message Resolver")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
}
