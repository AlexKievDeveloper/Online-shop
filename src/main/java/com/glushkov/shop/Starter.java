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
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.MultipartConfigElement;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        PropertyReader propertyReader = new PropertyReader();
        Properties properties = propertyReader.getProperties();

        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setURL(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));

        Flyway flyway = Flyway.configure().dataSource(properties.getProperty("db.url"),
                properties.getProperty("db.user"), properties.getProperty("db.password")).load();

        flyway.migrate();

        JdbcProductDao jdbcUserDao = new JdbcProductDao(dataSource);
        ProductService productService = new ProductService(jdbcUserDao);

        AllProductsServlet allProductsServlet = new AllProductsServlet(productService);
        SearchProductServlet searchProductServlet = new SearchProductServlet(productService);
        ViewProductServlet viewProductServlet = new ViewProductServlet(productService);
        EditProductServlet editProductServlet = new EditProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        AddProductServlet addProductServlet = new AddProductServlet(productService);
        AboutServlet aboutServlet = new AboutServlet();

        ServletHolder servletHolder = new ServletHolder(addProductServlet);
        servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("src/main/resources/webapp/static/img",
                1048576, 1048576, 262144));

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setErrorHandler(new DefaultErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "/home");
        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "");
        servletContextHandler.addServlet(new ServletHolder(aboutServlet), "/about");
        servletContextHandler.addServlet(new ServletHolder(searchProductServlet), "/search");
        servletContextHandler.addServlet(new ServletHolder(viewProductServlet), "/view");
        servletContextHandler.addServlet(new ServletHolder(editProductServlet), "/edit");
        servletContextHandler.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        servletContextHandler.addServlet(servletHolder, "/add");

        Resource resource = JarFileResource.newClassPathResource("webapp/static");
        servletContextHandler.setBaseResource(resource);
        servletContextHandler.addServlet(DefaultServlet.class, "/*");

        Server server = new Server(Integer.parseInt(properties.getProperty("port")));
        server.setHandler(servletContextHandler);
        server.start();


    }
}


