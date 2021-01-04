package com.glushkov.shop.entity;

public enum Role {
    ADMIN("ADMIN"), USER("USER"), GUEST("GUEST");

    private final String role;

    Role(String userRole) {
        this.role = userRole;
    }

    public String getUserRole() {
        return role;
    }
}
