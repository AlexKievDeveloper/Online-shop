package com.glushkov.shop.web.filter;

import com.glushkov.shop.ServiceLocator;
import com.glushkov.shop.entity.Role;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

public class AdminRoleSecurityFilter implements Filter {
    private SecurityService securityService = ServiceLocator.getService("securityService");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Cookie[] cookies = httpServletRequest.getCookies();
        boolean isAuth = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    Session session = securityService.getSession(token);
                    if (session != null) {
                        if (EnumSet.of(Role.ADMIN).contains(session.getUser().getRole())) {
                            isAuth = true;
                        }
                    }
                    break;
                }
            }
        }
        if (isAuth) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void destroy() {
    }
}

