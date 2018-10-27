package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.ProductStatusType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<Product> findProductByAddress(String address) {
        try {
            Product product = jdbcTemplate.queryForObject(
                    "select id, name, address, producer, price, status from products where address = ?",
                    new ProductMapper(), address);
            return Optional.of(product);
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    public Optional<Product> findProductById(long id) {
        try {
            Product product = jdbcTemplate.queryForObject(
                    "select id, name, address, producer, price, status from products where id = ?",
                    new ProductMapper(), id);
            return Optional.of(product);
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }


    public List<Product> listProducts() {
        return jdbcTemplate.query(
                "select id, name, address, producer, price, status from products " +
                        "where status = 'ACTIVE'", new ProductMapper());
    }

    private static class ProductMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String producer = resultSet.getString("producer");
            long currentPrice = resultSet.getLong("price");
            ProductStatusType status = ProductStatusType.valueOf(resultSet.getString("status"));
            return new Product(id, name, address, producer, currentPrice, status);
        }
    }

    public void createProduct(Product product) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into products(id, name, address, producer, price, status) values(?, ?, ?, ?, ?, 'ACTIVE')"
                );
                ps.setLong(1, product.getId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getAddress());
                ps.setString(4, product.getProducer());
                ps.setLong(5, product.getCurrentPrice());

                return ps;
            }
        });
    }

    public void updateProduct(long id, Product product) {
        jdbcTemplate.update(
                "update products set id = ?, name = ?, address = ?, producer = ?, price = ?, status = ? where id = ?",
                product.getId(),
                product.getName(),
                product.getAddress(),
                product.getProducer(),
                product.getCurrentPrice(),
                product.getStatus().toString(),
                id);
    }

    public void deleteProduct(long id) {
        jdbcTemplate.update("update products set status = 'INACTIVE' where id = ?", id);
    }
}