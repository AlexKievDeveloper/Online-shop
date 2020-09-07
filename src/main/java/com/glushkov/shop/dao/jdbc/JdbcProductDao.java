package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.dao.ProductDao;
import com.glushkov.shop.dao.jdbc.mapper.ProductRowMapper;
import com.glushkov.shop.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private static final String FIND_BY_ID = "SELECT id, name, price, description, image FROM products WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT id, name, price, description, image FROM products WHERE name ILIKE ?;";
    private static final String FIND_ALL = "SELECT id, name, price, description, image FROM products;";
    private static final String SAVE = "INSERT INTO products(name, price, description, image) VALUES (?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE products SET name = ?, price = ?, description = ? , image = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM products WHERE id = ?;";
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Product findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new RuntimeException("No data found for id: " + id);
                }

                if (!resultSet.isLast()) {//TODO resultSet.next() изменит резалт сет и появится ошибка: нет данных
                    throw new RuntimeException("More than one row found for id: " + id);
                }
                return PRODUCT_ROW_MAPPER.mapRow(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error while getting user from database, id:  {}", id, e);
            throw new RuntimeException("Error while getting user from database, id: ".concat(String.valueOf(id)), e);
        }
    }

    @Override
    public List<Product> findByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {

            preparedStatement.setString(1, "%" + name + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Product> productList = new ArrayList<>();

                while (resultSet.next()) {
                    productList.add(PRODUCT_ROW_MAPPER.mapRow(resultSet));
                }

                return productList;
            }
        } catch (SQLException e) {
            logger.error("Error while getting product from database by name: {}", name, e);
            throw new RuntimeException("Error while getting product from database by name: ".concat(name), e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {

            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(PRODUCT_ROW_MAPPER.mapRow(resultSet));
            }
            return productList;
        } catch (SQLException e) {
            logger.error("Error while getting products from database", e);
            throw new RuntimeException("Error while getting products from database", e);
        }
    }

    @Override
    public void save(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while saving product to database", e);
            throw new RuntimeException("Error while saving product to database", e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.setInt(5, product.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error("Error while updating product by id: {}", product.getId(), e);
            throw new RuntimeException("Error while updating product by id: ".concat(String.valueOf(product.getId())), e);
        }
    }

    @Override
    public void delete(int productId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Error while deleting product from database by id: {}", productId);
            throw new RuntimeException("Error while deleting product from database by id: ".concat(String.valueOf(productId)), e);
        }
    }
}
