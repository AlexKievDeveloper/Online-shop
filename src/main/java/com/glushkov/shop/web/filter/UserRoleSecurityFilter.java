package com.glushkov.shop.web.filter;

import com.glushkov.shop.entity.Role;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;
import java.util.Collection;
import java.util.EnumSet;

@Component
@WebFilter(urlPatterns = {"/", "/home", "/search", "/view/*", "/logout", "/cart"})
public class UserRoleSecurityFilter extends AbstractSecurityFilter {

    @Override
    Collection<Role> getRequiredRoles() {
        return EnumSet.of(Role.ADMIN, Role.USER);
    }
}
