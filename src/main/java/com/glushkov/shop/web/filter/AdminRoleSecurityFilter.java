package com.glushkov.shop.web.filter;

import com.glushkov.shop.entity.Role;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;
import java.util.Collection;
import java.util.EnumSet;

@Component
@WebFilter(urlPatterns = {"/add", "/edit", "/delete"})
public class AdminRoleSecurityFilter extends AbstractSecurityFilter {

    @Override
    Collection<Role> getRequiredRoles() {
        return EnumSet.of(Role.ADMIN);
    }
}
