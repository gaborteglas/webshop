package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public List<Product> listAllProducts() {
        return sortProductsByIdThenName(
                jdbcTemplate.query("select id, name, address, producer, price from products", new ProductMapper())
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

    public void createProduct (long id, String name, String address, String producer, long currentPrice) {
        List<Product> result = jdbcTemplate.query("select id, name, address, producer, price from products where id = ? OR address = ?", new ProductMapper(), id, address);
        if (result.size() == 0) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement("insert into products(id, name, address, producer, price) values(?, ?, ?, ?, ?)");
                    ps.setLong(1, id);
                    ps.setString(2, name);
                    ps.setString(3, address);
                    ps.setString(4, producer);
                    ps.setLong(5, currentPrice);
                    return ps;
                }
            });
        } else {
            throw new DuplicateProductException("A product with this id or address already exists.");
        }
    }

    public void updateProduct (long id, long newId, String name, String address, String producer, long currentPrice) {
        List<Product> result = jdbcTemplate.query("select id, name, address, producer, price from products where (id = ? or address = ?) and id <> ? ", new ProductMapper(), newId, address, id);
        if (result.size() == 0) {
            jdbcTemplate.update("update products set id = ?, name = ?, address = ?, producer = ?, price = ? where id = ?", newId, name, address, producer, currentPrice, id);
        } else {
            throw new DuplicateProductException("A product with this id or address already exists.");
        }
    }

    public void deleteProduct (long id) {
        jdbcTemplate.update("update products set deleted = 'inactive' where id = ?", id);
    }





}