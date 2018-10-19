package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository

public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class ProductMapper implements RowMapper<Optional<Product>> {
        @Override
        public Optional<Product> mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String producer = resultSet.getString("producer");
            Long currentPrice = resultSet.getLong("price");
            Product product = new Product(id, name, address, producer, currentPrice);
            return Optional.of(product);
        }
    }

    public Optional<Product> findProductByAddress(String address) {
        return jdbcTemplate.queryForObject("select id, name, address, producer, price from products where address = ?",
                new ProductMapper(), address);
    }
}