package com.glushkov.shop.web.filter;

import com.glushkov.shop.entity.Role;

import java.util.Collection;
import java.util.EnumSet;

public class AdminRoleSecurityFilter extends AbstractSecurityFilter {
    @Override
    Collection<Role> getRequairedRoles() {
        return EnumSet.of(Role.ADMIN);
    }
}

