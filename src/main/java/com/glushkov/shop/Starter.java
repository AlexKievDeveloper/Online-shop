//package com.glushkov.shop;
/*
import com.glushkov.shop.dao.jdbc.ConnectionManager;
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
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

import java.io.File;

public class Starter {
   public static void main(String[] args) throws Exception {
         val dataSource = new PGSimpleDataSource();

       dataSource.setURL(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));

        DataSource dataSource = new ConnectionManager();

        val jdbcUserDao = new JdbcProductDao(dataSource);
        val productService = new ProductService(jdbcUserDao);

        ServiceLocator.getService("productService");

        val allProductsServlet = new AllProductsServlet((ProductService) ServiceLocator.getService("productService"));
        val searchProductServlet = new SearchProductServlet((ProductService) ServiceLocator.getService("productService"));
        val viewProductServlet = new ViewProductServlet((ProductService) ServiceLocator.getService("productService"));
        val editProductServlet = new EditProductServlet((ProductService) ServiceLocator.getService("productService"));
        val deleteProductServlet = new DeleteProductServlet((ProductService) ServiceLocator.getService("productService"));
        val addProductServlet = new AddProductServlet((ProductService) ServiceLocator.getService("productService"));

        val servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setErrorHandler(new DefaultErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "/home");
        servletContextHandler.addServlet(new ServletHolder(allProductsServlet), "");
        servletContextHandler.addServlet(new ServletHolder(searchProductServlet), "/search");
        servletContextHandler.addServlet(new ServletHolder(viewProductServlet), "/view/*");
        servletContextHandler.addServlet(new ServletHolder(editProductServlet), "/edit/*");
        servletContextHandler.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        servletContextHandler.addServlet(new ServletHolder(addProductServlet), "/add");


        val resource = JarFileResource.newClassPathResource("static");
        servletContextHandler.setBaseResource(resource);
        servletContextHandler.addServlet(DefaultServlet.class, "/*");

        val propertyReader = new PropertyReader();
        val properties = propertyReader.getProperties();

        val server = new Server(Integer.parseInt(properties.getProperty("port")));
        server.setHandler(servletContextHandler);
        server.start();
        server.join();


       Server server = new Server(8080);
       setWebAppServlet(server);
       server.start();
       server.join();
    }

    private static void setWebAppServlet(Server server){
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setWar(new File("target/online-shop-1.0-SNAPSHOT.war").getAbsolutePath());
        server.setHandler(webAppContext);
    }
}
*/

