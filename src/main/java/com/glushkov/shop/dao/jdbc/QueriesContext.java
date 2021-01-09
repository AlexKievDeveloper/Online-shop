package com.glushkov.shop.dao.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueriesContext {
    /**
     * JdbcProductDao queries
     */

    @Bean
    public String findById() {
        return "SELECT id, name, price, description, image FROM products WHERE id = ?";
    }

    @Bean
    public String findByName() {
        return "SELECT id, name, price, description, image FROM products WHERE name ILIKE '%'||?||'%';";
    }

    @Bean
    public String findAll() {
        return "SELECT id, name, price, description, image FROM products;";
    }

    @Bean
    public String save() {
        return "INSERT INTO products(name, price, description, image) VALUES (?, ?, ?, ?);";
    }

    @Bean
    public String update() {
        return "UPDATE products SET name = ?, price = ?, description = ? , image = ? WHERE id = ?;";
    }

    @Bean
    public String delete() {
        return "DELETE FROM products WHERE id = ?;";
    }

    /**
     * JdbcUserDao queries
     */

    @Bean
    public String findUserByLogin() {
        return "SELECT id, login, password, role, sole FROM users WHERE login = ?";
    }

    @Bean
    public String saveUser() {
        return "INSERT INTO users(login, password, role, sole) VALUES (?, ?, ?, ?);";
    }

    @Bean
    public String isExist() {
        return "SELECT EXISTS(select 1 from users where login=?)";
    }
}
