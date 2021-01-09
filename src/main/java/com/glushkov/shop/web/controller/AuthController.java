package com.glushkov.shop.web.controller;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import com.glushkov.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
        model.addAttribute("message", message);
        return "login.html";
    }

    @GetMapping("/logout")
    protected String logout(@RequestAttribute Session session) {
        securityService.logout(session.getToken());
        return "redirect:/login";
    }

    @PostMapping("/registration")
    protected String registration(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        boolean isLoginExist = userService.isLoginExist(login);

        if (!isLoginExist) {
            String sole = UUID.randomUUID().toString();

            User user = User.builder()
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
            return "redirect:/home";
        } else {
            return "redirect:/registration?message=Sorry, login ".concat(login)
                    .concat(" already exists. Please try again with another login.");
        }
    }

    @PostMapping("/login")
    protected String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {

        Session session = securityService.login(login, password);
        if (session != null) {
            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge(sessionMaxAge);
            response.addCookie(cookie);
            log.info("User logged in");
            return "redirect:/home";
        } else {
            log.info("Session is null");
            return "redirect:/login?message=Access denied. Please login and try again.";
        }
    }
}
