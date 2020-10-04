package com.glushkov.shop.security;

import com.glushkov.shop.entity.Product;
import com.glushkov.shop.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Session {
    private String token;
    private User user;
    private LocalDateTime expireDate;
    private List<Product> cart;
}
