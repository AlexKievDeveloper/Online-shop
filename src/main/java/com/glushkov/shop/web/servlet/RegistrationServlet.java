package com.glushkov.shop.web.servlet;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.UserService;
import com.glushkov.shop.web.templater.PageGenerator;
import lombok.val;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static com.glushkov.shop.web.WebConstants.CONTENT_TYPE;

public class RegistrationServlet extends HttpServlet {
    private UserService userService = ServiceLocator.getService("userService");
    private SecurityService securityService = ServiceLocator.getService("securityService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val parameters = new HashMap<String, Object>();
        response.setContentType(CONTENT_TYPE);
        parameters.put("message", request.getParameter("message"));
        PageGenerator.instance().process("registration", parameters, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        val login = request.getParameter("login");
        boolean isLoginExist = userService.isLoginExist(login);

        if (!isLoginExist) {
            val password = request.getParameter("password");
            val sole = UUID.randomUUID().toString();

            val user = User.builder()
                    .login(login)
                    .password(DigestUtils.sha256Hex(sole.concat(password)))
                    .sole(sole)
                    .role(Role.USER)
                    .build();

            userService.save(user);

            Session session = securityService.login(login, password);
            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/registration?message=Sorry, login ".concat(login)
                    .concat(" already exists. Please try again with another login."));
        }
    }
}
