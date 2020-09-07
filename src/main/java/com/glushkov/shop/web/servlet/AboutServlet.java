package com.glushkov.shop.web.servlet;

import com.glushkov.shop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AboutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        PageGenerator pageGenerator = PageGenerator.instance();
        pageGenerator.process("/about", map, response.getWriter());
    }
}
