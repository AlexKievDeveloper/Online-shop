package com.glushkov.shop.web.filter;

import com.glushkov.shop.entity.Role;
import com.glushkov.shop.security.DefaultSecurityService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;
import java.util.Collection;
import java.util.EnumSet;

@Component
@NoArgsConstructor
@WebFilter(urlPatterns = {"/", "/home", "/search", "/view/*", "/logout", "/cart"})
public class UserRoleSecurityFilter extends AbstractSecurityFilter {
    @Autowired
    public UserRoleSecurityFilter(DefaultSecurityService securityService) {
        super(securityService);
    }

    @Override
    Collection<Role> getRequiredRoles() {
        return EnumSet.of(Role.ADMIN, Role.USER);
    }
}
