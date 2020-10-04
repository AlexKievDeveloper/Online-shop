package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
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
            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login?message=Access denied. Please login and try again.");
        }
    }
}





/*    private DefaultUserService defaultUserService = ServiceLocator.getService("userService");
        private Map<String, Role> tokensRoleMap = DefaultAuthenticationService.getTokensRoleMap();*/

/*              1) Получили логин и пароль
                2) Делаем запрос получить соль где логин равняется логин
                3) password(DigestUtils.sha1Hex(sole.concat(password))
                4) findUser(login, пароль из пункта 3)*/

/*        val userToken = UUID.randomUUID().toString();
        val cookie = new Cookie("user-token", userToken);


        val user = defaultUserService.findUser(login, password);

        if (user != null) {
            tokensRoleMap.put(cookie.getValue(), user.getRole());
            response.sendRedirect("/");
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("message", "Incorrect login or password. Please try again.");
            tokensRoleMap.put(cookie.getValue(), Role.GUEST);
            response.setContentType(CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PageGenerator.instance().process("login", parameters, response.getWriter());
        }*/

//TODO 1) при указании максимального возраста куки, время создания куки на 3 часа раньше чем у меня в системе и в приложении
// 2) в мозилле и хроме сессионная кука не удалилась ни после закрытия браузера ни после перезагрузки системы
