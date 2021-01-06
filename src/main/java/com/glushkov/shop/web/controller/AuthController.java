package com.glushkov.shop.web.controller;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final SecurityService securityService;
    private final UserService userService;
    @Value("${session.max-age}")
    private int sessionMaxAge;

    @GetMapping("/registration")
    protected String getRegistrationPage(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("message", message);
        return "registration.html";
    }

    @GetMapping("/login")
    protected String getLoginPage(Model model, @RequestParam(required = false) String message) {
        log.info("Got request for login page");
        model.addAttribute("message", message);
        return "login.html";
    }

    @GetMapping("/logout")
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = (Session) request.getAttribute("session");
        log.info("Session: {}", session);
        securityService.logout(session.getToken());
        response.sendRedirect("/login");
    }

    @PostMapping("/registration")
    protected void registration(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            cookie.setMaxAge(sessionMaxAge);
            response.addCookie(cookie);
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/registration?message=Sorry, login ".concat(login)
                    .concat(" already exists. Please try again with another login."));
        }
    }

    @PostMapping("/login")
    protected void login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) throws IOException {

        Session session = securityService.login(login, password);
        log.info("Before checking session for null");
        if (session != null) {
            log.info("Before cookie creating");
            Cookie cookie = new Cookie("user-token", session.getToken());
            log.info("After cookie creating");
            cookie.setMaxAge(sessionMaxAge);
            log.info("After cookie setting max age");
            response.addCookie(cookie);
            log.info("I am going to return home.html");
            response.sendRedirect("/home");
        } else {
            log.info("Session is null");
            response.sendRedirect("/login?message=Access denied. Please login and try again.");
        }
    }
}
