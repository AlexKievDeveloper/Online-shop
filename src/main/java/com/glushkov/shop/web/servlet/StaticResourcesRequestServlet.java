package com.glushkov.shop.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StaticResourcesRequestServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
/*          if (request.getRequestURI().equals("/")){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users");
            requestDispatcher.forward(request, response);
            return;
        }*/

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
             InputStream inputStream = readContent(request.getRequestURI())) {

            byte[] buffer = new byte[8192];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, count);
            }
        }
    }

    InputStream readContent(String uri) {
        return getClass().getClassLoader().getResourceAsStream("static" + uri);
    }
}