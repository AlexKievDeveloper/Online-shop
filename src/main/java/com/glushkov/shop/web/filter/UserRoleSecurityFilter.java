package com.glushkov.shop.web.filter;

import com.glushkov.shop.entity.Role;

import java.util.Collection;
import java.util.EnumSet;

public class UserRoleSecurityFilter extends AbstractSecurityFilter {
    @Override
    Collection<Role> getRequairedRoles() {
        return EnumSet.of(Role.ADMIN, Role.USER);
    }
}

