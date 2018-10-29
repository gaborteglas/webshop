package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Category;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<Category> findCategoryById(long id) {
        try {
            Category category = jdbcTemplate.queryForObject(
                    "SELECT id, name, position_number FROM category WHERE id = ?",
                    new CategoryMapper(), id);
            return Optional.of(category);
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }


//    public List<Product> listProducts() {
//        return jdbcTemplate.query(
//                "select id, name, address, producer, price, status from products " +
//                        "where status = 'ACTIVE'", new ProductDao.ProductMapper());
//    }

    private static class CategoryMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long positionNumber = resultSet.getLong("position_number");
            return new Category(id, name, positionNumber);
        }
    }

//    public void createProduct(Product product) {
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps = connection.prepareStatement(
//                        "insert into products(id, name, address, producer, price, status) values(?, ?, ?, ?, ?, 'ACTIVE')"
//                );
//                ps.setLong(1, product.getId());
//                ps.setString(2, product.getName());
//                ps.setString(3, product.getAddress());
//                ps.setString(4, product.getProducer());
//                ps.setLong(5, product.getCurrentPrice());
//
//                return ps;
//            }
//        });
//    }
//
//    public void updateProduct(long id, Product product) {
//        jdbcTemplate.update(
//                "update products set id = ?, name = ?, address = ?, producer = ?, price = ?, status = ? where id = ?",
//                product.getId(),
//                product.getName(),
//                product.getAddress(),
//                product.getProducer(),
//                product.getCurrentPrice(),
//                product.getStatus().toString(),
//                id);
//    }
//
//    public void deleteProduct(long id) {
//        jdbcTemplate.update("update products set status = 'INACTIVE' where id = ?", id);
//    }
}
