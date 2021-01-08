package com.glushkov.shop.web.filter;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.security.SecurityService;
import com.glushkov.shop.security.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
public abstract class AbstractSecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        WebApplicationContext webApplicationContext = (WebApplicationContext) request.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        SecurityService securityService = (SecurityService) webApplicationContext.getBean("defaultSecurityService");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Cookie[] cookies = httpServletRequest.getCookies();
        boolean isAuth = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    log.info("Token: {}", token);
                    log.info("Security service: {}", securityService);
                    Session session = securityService.getSession(token);
                    if (session != null) {
                        if (getRequiredRoles().contains(session.getUser().getRole())) {
                            isAuth = true;
                            request.setAttribute("session", session);
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

    abstract Collection<Role> getRequiredRoles();
}
