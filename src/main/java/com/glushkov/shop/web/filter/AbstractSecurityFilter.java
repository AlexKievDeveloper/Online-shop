package com.glushkov.shop.web.filter;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.security.DefaultSecurityService;
import com.glushkov.shop.security.Session;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@NoArgsConstructor
//Происходит вызов конструктора без параметров, игнорируется вызов all args constructor и не инжектится сервис
@AllArgsConstructor
public abstract class AbstractSecurityFilter implements Filter {
    private DefaultSecurityService securityService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
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










/*    @Autowired
    public AbstractSecurityFilter() {
        setSecurityService(DefaultSecurityService securityService);
    }

    @Autowired
    public final void setSecurityService(DefaultSecurityService securityService) {
        this.securityService = securityService;
    }*/