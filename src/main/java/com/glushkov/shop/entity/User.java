package com.glushkov.shop.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private int id;
    private String login;
    private String password;
    private String sole;
    private Role role;
}
