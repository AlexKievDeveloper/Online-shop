package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.util.PropertyReader;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.glushkov.shop.web.WebConstants.CONTENT_TYPE;

public class LoginServlet extends HttpServlet {
    private SecurityService securityService = ServiceLocator.getService("securityService");
    private PropertyReader propertyReader = ServiceLocator.getPropertyReader();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val parameters = new HashMap<String, Object>();
        response.setContentType(CONTENT_TYPE);
        parameters.put("message", request.getParameter("message"));
        PageGenerator.instance().process("login", parameters, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val login = request.getParameter("login");
        val password = request.getParameter("password");

        Session session = securityService.login(login, password);
        if (session != null) {
            int sessionMaxAge = Integer.parseInt(propertyReader.getProperties().getProperty("session.max-age"));
            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge(sessionMaxAge);
            response.addCookie(cookie);
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login?message=Access denied. Please login and try again.");
        }
    }
}

//TODO 1) при указании максимального возраста куки, время создания куки на 3 часа раньше чем у меня в системе и в приложении
// 2) в мозилле и хроме сессионная кука не удалилась ни после закрытия браузера ни после перезагрузки системы
