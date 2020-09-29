package com.glushkov.shop.entity;

public enum Role {//TODO разобраться как в идеале это должно работать?
    ADMIN("ADMIN"),USER("USER"),GUEST("GUEST");

    private final String role;

    Role(String userRole) {
        this.role = userRole;
    }

    public String getUserRole() {
        return role;
    }
}
