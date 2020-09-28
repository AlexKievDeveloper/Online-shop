package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Role;
import com.glushkov.shop.service.UserService;
import com.glushkov.shop.service.AuthenticationService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private final String CONTENT_TYPE = "text/html;charset=utf-8";
    private final UserService userService = ServiceLocator.getService("userService");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("login", response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val login = request.getParameter("login");
        val password = request.getParameter("password");

        val userToken = UUID.randomUUID().toString();
        val cookie = new Cookie("user-token", userToken);
        response.addCookie(cookie);

        val user = userService.findUser(login, password);

        if (user != null) {
            AuthenticationService.getTokensRoleMap().put(cookie.getValue(), user.getRole());
            response.sendRedirect("/");
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("message", "Incorrect login or password. Please try again.");
            AuthenticationService.getTokensRoleMap().put(cookie.getValue(), Role.GUEST);
            response.setContentType(CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PageGenerator.instance().process("login", parameters, response.getWriter());
        }
    }
}
//TODO 1) при указании максимального возраста куки, время создания куки на 3 часа раньше чем у меня в системе и в приложении
// 2) в мозилее и хроме сессионная кука не удалилась ни после закрытия браузера ни после перезагрузки системы