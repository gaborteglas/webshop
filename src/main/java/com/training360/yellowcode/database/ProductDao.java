package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<Product> findProductByAddress(String address) {
        try {
            Product product = jdbcTemplate.queryForObject("select id, name, address, producer, price from products where address = ?",
                    new ProductMapper(), address);
            return Optional.of(product);
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    public List<Product> listProducts() {
        return sortProductsByIdThenName(
                jdbcTemplate.query("select id, name, address, producer, price from products where deleted = 'active'", new ProductMapper())
        );
    }

    private List<Product> sortProductsByIdThenName(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getName).thenComparing(Product::getProducer))
                .collect(Collectors.toList());
    }

    private static class ProductMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String producer = resultSet.getString("producer");
            Long currentPrice = resultSet.getLong("price");
            Product product = new Product(id, name, address, producer, currentPrice);
            return product;
        }
    }
}