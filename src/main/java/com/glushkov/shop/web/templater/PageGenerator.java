package com.glushkov.shop.web.templater;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.Map;

public class PageGenerator {

    private static PageGenerator pageGenerator;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }
//Получить шаблон из файла
//Записать в шаблон те строки которые нам нужны
//Получить строку с готовой страницей, записать её в response
    public String getPage(String filename, Map<String, Object> pageVariables) {



        return null;//*template.process(pageVariables)*//* templateGenerator.process(filename, pageVariables).getContent();
    }

/*
   private PageGenerator() {
        cfg = new Configuration("/templates");
    }
*/
}
