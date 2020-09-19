package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.dao.ProductDao;
import com.glushkov.shop.dao.jdbc.mapper.ProductRowMapper;
import com.glushkov.shop.entity.Product;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private static final String FIND_BY_ID = "SELECT id, name, price, description, image FROM products WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT id, name, price, description, image FROM products WHERE name ILIKE '%'||?||'%';";
    private static final String FIND_ALL = "SELECT id, name, price, description, image FROM products;";
    private static final String SAVE = "INSERT INTO products(name, price, description, image) VALUES (?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE products SET name = ?, price = ?, description = ? , image = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM products WHERE id = ?;";
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private final DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @SneakyThrows
    public Product findById(int id) {
        @Cleanup val connection = dataSource.getConnection();
        @Cleanup val preparedStatement = connection.prepareStatement(FIND_BY_ID);
        preparedStatement.setInt(1, id);
        @Cleanup val resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            throw new RuntimeException("No data found for id: " + id);
        }
        val product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
        if (resultSet.next()) {
            throw new RuntimeException("More than one row found for id: " + id);
        }

        return product;
    }

    @Override
    @SneakyThrows
    public List<Product> findByName(String name) {
        @Cleanup val connection = dataSource.getConnection();
        @Cleanup val preparedStatement = connection.prepareStatement(FIND_BY_NAME);
        preparedStatement.setString(1, name);
        @Cleanup val resultSet = preparedStatement.executeQuery();
        val productList = new ArrayList<Product>();

        while (resultSet.next()) {
            productList.add(PRODUCT_ROW_MAPPER.mapRow(resultSet));
        }

        return productList;
    }

    @Override
    @SneakyThrows
    public List<Product> findAll() {
        @Cleanup val connection = dataSource.getConnection();
        @Cleanup val statement = connection.createStatement();
        @Cleanup val resultSet = statement.executeQuery(FIND_ALL);
        val productList = new ArrayList<Product>();
        while (resultSet.next()) {
            productList.add(PRODUCT_ROW_MAPPER.mapRow(resultSet));
        }
        return productList;
    }

    @Override
    @SneakyThrows
    public void save(Product product) {
        @Cleanup val connection = dataSource.getConnection();
        @Cleanup val preparedStatement = connection.prepareStatement(SAVE);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setString(3, product.getDescription());
        preparedStatement.setString(4, product.getImage());
        preparedStatement.executeUpdate();
    }

    @Override
    @SneakyThrows
    public void update(Product product) {
        @Cleanup val connection = dataSource.getConnection();
        @Cleanup val preparedStatement = connection.prepareStatement(UPDATE);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setString(3, product.getDescription());
        preparedStatement.setString(4, product.getImage());
        preparedStatement.setInt(5, product.getId());
        preparedStatement.execute();
    }

    @Override
    @SneakyThrows
    public void delete(int productId) {
        @Cleanup val connection = dataSource.getConnection();
        @Cleanup val preparedStatement = connection.prepareStatement(DELETE);
        preparedStatement.setInt(1, productId);
        preparedStatement.execute();
    }
}
