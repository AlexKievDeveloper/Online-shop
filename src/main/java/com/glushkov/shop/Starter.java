package com.glushkov.shop;

import com.glushkov.shop.dao.jdbc.JdbcProductDao;
import com.glushkov.shop.service.ProductService;
import com.glushkov.shop.util.PropertyReader;
import com.glushkov.shop.web.handler.DefaultErrorHandler;
import com.glushkov.shop.web.servlet.*;
import lombok.val;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.JarFileResource;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

public class Starter {
    public static void main(String[] args) throws Exception {
        val propertyReader = new PropertyReader();
        val properties = propertyReader.getProperties();

        val dataSource = new PGSimpleDataSource();

        dataSource.setURL(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));

        val flyway = Flyway.configure().dataSource(properties.getProperty("db.url"),
                properties.getProperty("db.user"), properties.getProperty("db.password")).load();

        flyway.migrate();

        val jdbcUserDao = new JdbcProductDao(dataSource);
        val productService = new ProductService(jdbcUserDao);

        val allProductsServlet = new AllProductsServlet(productService);
        val searchProductServlet = new SearchProductServlet(productService);
        val viewProductServlet = new ViewProductServlet(productService);
        val editProductServlet = new EditProductServlet(productService);
        val deleteProductServlet = new DeleteProductServlet(productService);
        val addProductServlet = new AddProductServlet(productService);

        val servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setErrorHandler(new DefaultErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "/home");
        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "");
        servletContextHandler.addServlet(new ServletHolder(searchProductServlet), "/search");
        servletContextHandler.addServlet(new ServletHolder(viewProductServlet), "/view");
        servletContextHandler.addServlet(new ServletHolder(editProductServlet), "/edit");
        servletContextHandler.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        servletContextHandler.addServlet(new ServletHolder(addProductServlet), "/add");

        val resource = JarFileResource.newClassPathResource("webapp/static");
        servletContextHandler.setBaseResource(resource);
        servletContextHandler.addServlet(DefaultServlet.class, "/*");

        val server = new Server(Integer.parseInt(properties.getProperty("port")));
        server.setHandler(servletContextHandler);
        server.start();
    }
}


