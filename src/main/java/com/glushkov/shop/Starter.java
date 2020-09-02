package com.glushkov.shop;

import com.glushkov.shop.dao.jdbc.JdbcProductDao;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.handler.DefaultErrorHandler;
import com.glushkov.shop.web.servlet.AllProductsServlet;
import com.glushkov.shop.web.servlet.StaticResourcesRequestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.JarFileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        PropertyReader propertyReader = new PropertyReader();
        Properties properties = propertyReader.getProperties();

        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setURL(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));

        JdbcProductDao jdbcUserDao = new JdbcProductDao(dataSource);

        ProductService productService = new ProductService(jdbcUserDao);

        AllProductsServlet allProductsServlet = new AllProductsServlet(productService);
        StaticResourcesRequestServlet staticResourcesRequestServlet = new StaticResourcesRequestServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setErrorHandler(new DefaultErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "/home");
        servletContextHandler.addServlet(new ServletHolder(staticResourcesRequestServlet), "/*");

/*        Resource resource = JarFileResource.newClassPathResource("/static");
        servletContextHandler.setBaseResource(resource);
        servletContextHandler.addServlet(DefaultServlet.class, "/*");*/

        Server server = new Server(Integer.parseInt(properties.getProperty("port")));
        server.setHandler(servletContextHandler);
        server.start();
    }
}
