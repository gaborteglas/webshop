package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Category;
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


    public List<Category> listCategorys() {
        return jdbcTemplate.query(
                "select id, name, position_number FROM category ORDER BY position_number",
                new CategoryDao.CategoryMapper());
    }

    private static class CategoryMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long positionNumber = resultSet.getLong("position_number");
            return new Category(id, name, positionNumber);
        }
    }

    public void createCategory(Category category) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into category(name, position_number) values(?, ?)"
                );
                ps.setString(2, category.getName());
                ps.setLong(5, category.getPositionNumber());

                return ps;
            }
        });
    }

    public void updateCategory(Category category) {
        jdbcTemplate.update(
                "update category set id = ?, name = ?, position_number = ? where id = ?",
                category.getId(),
                category.getName(),
                category.getPositionNumber(),
                category.getId());
    }

    public void deleteCategoryUpdateProducts(long id) {
        jdbcTemplate.update("update products set category = null where id = ?", id);
    }

    public void deleteCategory(Category category) {
        jdbcTemplate.update("delete from category where id = ?", category.getId());
    }

    public void updateCategoryPosition(long id) {
        jdbcTemplate.update("update category set position_number = position_number + 1 where id >= ?", id );
    }

    public void updateCategoryPositionAfterDelete(long id) {
        jdbcTemplate.update("update category set position_number = position_number - 1 where id >= ?", id );
    }
}