package com.glushkov.shop;

import com.glushkov.shop.web.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class OnlineShopAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootApplicationContext.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebApplicationContext.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }
}

