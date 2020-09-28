package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.dao.ProductDao;
import com.glushkov.shop.dao.jdbc.mapper.ProductRowMapper;
import com.glushkov.shop.entity.Product;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    public Product findById(int id) {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setInt(1, id);

            try (val resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new RuntimeException("No data found for id: ".concat(String.valueOf(id)));
                }

                val product = PRODUCT_ROW_MAPPER.mapRow(resultSet);

                if (resultSet.next()) {
                    throw new RuntimeException("More than one row found for id: ".concat(String.valueOf(id)));
                }
                return product;
            }
        } catch (SQLException e) {
            log.error("Error while connection to DB, method findByID() id: {}", id, e);
            throw new RuntimeException("Error while connection to DB, method findByID() id: "
                    .concat(String.valueOf(id)), e);
        }
    }

    @Override
    public List<Product> findByName(String name) {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);

            try (val resultSet = preparedStatement.executeQuery()) {
                val productList = new ArrayList<Product>();

                while (resultSet.next()) {
                    productList.add(PRODUCT_ROW_MAPPER.mapRow(resultSet));
                }
                return productList;
            }
        } catch (SQLException e) {
            log.error("Error while connection to DB, method findByName() name: {}", name, e);
            throw new RuntimeException("Error while connection to DB, method findByName() name: ".concat(name), e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (val connection = dataSource.getConnection();
             val statement = connection.createStatement();
             val resultSet = statement.executeQuery(FIND_ALL)) {

            val productList = new ArrayList<Product>();
            while (resultSet.next()) {
                productList.add(PRODUCT_ROW_MAPPER.mapRow(resultSet));
            }
            return productList;
        } catch (SQLException e) {
            log.error("Error while connection to DB, method findAll()", e);
            throw new RuntimeException("Error while connection to DB, method findAll()", e);
        }
    }

    @Override
    public void save(Product product) {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(SAVE)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while connection to DB, method save()", e);
            throw new RuntimeException("Error while connection to DB, method save()", e);
        }
    }

    @Override
    public void update(Product product) {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.setInt(5, product.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Error while connection to DB, method update() id: {}", product.getId(), e);
            throw new RuntimeException("Error while connection to DB, method update() id: "
                    .concat(String.valueOf(product.getId())), e);
        }
    }

    @Override
    public void delete(int productId) {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Error while connection to DB, method delete() id: {}", productId, e);
            throw new RuntimeException("Error while connection to DB, method delete() id: "
                    .concat(String.valueOf(productId)), e);
        }
    }
}
