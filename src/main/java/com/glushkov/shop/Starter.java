package com.glushkov.shop;

import com.glushkov.shop.dao.jdbc.JdbcProductDao;
import com.glushkov.shop.reader.PropertyReader;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.web.handler.DefaultErrorHandler;
import com.glushkov.shop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.JarFileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.h2.jdbcx.JdbcDataSource;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        PropertyReader propertyReader = new PropertyReader();
        Properties properties = propertyReader.getProperties();

        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setURL(properties.getProperty("jdbc.host"));
        dataSource.setUser(properties.getProperty("jdbc.user"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        ;
        dataSource.getConnection();
        dataSource.setURL(properties.getProperty("jdbc.url"));

/*        Flyway flyway = Flyway.configure().dataSource(properties.getProperty("jdbc.host"),
                properties.getProperty("jdbc.user"), properties.getProperty("jdbc.password")).load();

        flyway.migrate();*/

        JdbcProductDao jdbcUserDao = new JdbcProductDao(dataSource);
        ProductService productService = new ProductService(jdbcUserDao);

        AllProductsServlet allProductsServlet = new AllProductsServlet(productService);
        SearchProductServlet searchProductServlet = new SearchProductServlet(productService);
        ViewProductServlet viewProductServlet = new ViewProductServlet(productService);
        EditProductServlet editProductServlet = new EditProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        AboutServlet aboutServlet = new AboutServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setErrorHandler(new DefaultErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "/home");
        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "");
        servletContextHandler.addServlet(new ServletHolder(aboutServlet), "/about");
        servletContextHandler.addServlet(new ServletHolder(searchProductServlet), "/search");
        servletContextHandler.addServlet(new ServletHolder(viewProductServlet), "/view");
        servletContextHandler.addServlet(new ServletHolder(editProductServlet), "/edit");
        servletContextHandler.addServlet(new ServletHolder(deleteProductServlet), "/delete");

        Resource resource = JarFileResource.newClassPathResource("webapp/static");
        servletContextHandler.setBaseResource(resource);
        servletContextHandler.addServlet(DefaultServlet.class, "/*");

        Server server = new Server(Integer.parseInt(properties.getProperty("port")));
        server.setHandler(servletContextHandler);
        server.start();
    }
}


