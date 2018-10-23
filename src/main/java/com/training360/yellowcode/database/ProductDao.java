package com.training360.yellowcode.database;

import com.training360.yellowcode.businesslogic.ProductService;
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
import java.text.Collator;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
            Product product = jdbcTemplate.queryForObject(
                    "select id, name, address, producer, price, status from products where address = ?",
                    new ProductMapper(), address);
            return Optional.of(product);
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    public List<Product> listProducts() {
        return sortProductsNameIdThenProducer(
                jdbcTemplate.query(
                        "select id, name, address, producer, price, status from products " +
                                "where status = 'active'", new ProductMapper())
        );
    }

    public List<Product> listAllProducts() {
        return sortProductsNameIdThenProducer(
                jdbcTemplate.query("select id, name, address, producer, price, status from products",
                        new ProductMapper())
        );
    }

    private List<Product> sortProductsNameIdThenProducer(List<Product> products) {
        Collator hungarianLocale = Collator.getInstance(new Locale("hu", "HU"));
        return products.stream()
                .sorted(Comparator.comparing(Product::getName,
                        Comparator.comparing(String::toLowerCase,
                                Comparator.nullsFirst(hungarianLocale)))
                        .thenComparing(Product::getProducer,
                                Comparator.comparing(String::toLowerCase,
                                        Comparator.nullsFirst(hungarianLocale))))
                .collect(Collectors.toList());
    }

    private static class ProductMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String producer = resultSet.getString("producer");
            Long currentPrice = resultSet.getLong("price");
            return new Product(id, name, producer, currentPrice);
        }
    }

    public void createProduct(Product product) {
        List<Product> result = jdbcTemplate.query(
                "select id, name, address, producer, price, status from products where id = ? OR name = ?",
                new ProductMapper(), product.getId(), product.getName());
        if (result.size() == 0) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into products(id, name, address, producer, price, status) values(?, ?, ?, ?, ?, 'active')"
                    );
                    throwIllegalArgumentExceptionIfPriceIsInvalid(product.getCurrentPrice());
                    ps.setLong(1, product.getId());
                    ps.setString(2, product.getName());
                    ps.setString(3, product.getAddress());
                    ps.setString(4, product.getProducer());
                    ps.setLong(5, product.getCurrentPrice());
                    ProductService.LOGGER.info(MessageFormat.format("Product added(id: {0}, name: {1}," +
                                    " address: {2}, producer: {3}, currentPrice: {4})", product.getId(), product.getName(),
                            product.getAddress(), product.getProducer(), product.getCurrentPrice()));
                    return ps;
                }
            });
        } else {
            throw new DuplicateProductException("A product with this id or address already exists.");
        }
    }

    public void updateProduct(long id, long newId, String name, String address, String producer, long currentPrice) {
        throwIllegalArgumentExceptionIfPriceIsInvalid(currentPrice);
        List<Product> result = jdbcTemplate.query(
                "select id, name, address, producer, price, status from products where (id = ? or address = ?) and id <> ? ",
                new ProductMapper(), newId, address, id);
        if (result.size() == 0) {
            jdbcTemplate.update(
                    "update products set id = ?, name = ?, address = ?, producer = ?, price = ? where id = ?",
                    newId, name, address, producer, currentPrice, id);
            ProductService.LOGGER.info(MessageFormat.format("Product modified to -> id: {0}, name: {1}," +
                    " address: {2}, producer: {3}, currentPrice: {4}", id, name, address, producer, currentPrice));
        } else {
            throw new DuplicateProductException("A product with this id or address already exists.");
        }
    }

    public void deleteProduct(long id) {
        jdbcTemplate.update("update products set status = 'inactive' where id = ?", id);
        ProductService.LOGGER.info(MessageFormat.format("Product with '{0}' id set to inactive", id));
    }

    private void throwIllegalArgumentExceptionIfPriceIsInvalid(long price) {
        if (price > 2_000_000 || price <= 0) {
            throw new IllegalArgumentException("Invalid price" + price);
        }
    }
}