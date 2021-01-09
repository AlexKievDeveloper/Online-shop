package com.glushkov.shop.dao.jdbc;

import com.glushkov.shop.dao.ProductDao;
import com.glushkov.shop.dao.jdbc.mapper.ProductRowMapper;
import com.glushkov.shop.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {
    @Autowired
    private String findById;
    @Autowired
    private String findByName;
    @Autowired
    private String findAll;
    @Autowired
    private String save;
    @Autowired
    private String update;
    @Autowired
    private String delete;

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private final DataSource dataSource;

    @Override
    public Product findById(int id) {
        try (val connection = dataSource.getConnection();
             val preparedStatement = connection.prepareStatement(findById)) {

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
             val preparedStatement = connection.prepareStatement(findByName)) {
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
             val resultSet = statement.executeQuery(findAll)) {

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
             val preparedStatement = connection.prepareStatement(save)) {

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
             val preparedStatement = connection.prepareStatement(update)) {

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
             val preparedStatement = connection.prepareStatement(delete)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Error while connection to DB, method delete() id: {}", productId, e);
            throw new RuntimeException("Error while connection to DB, method delete() id: "
                    .concat(String.valueOf(productId)), e);
        }
    }
}
