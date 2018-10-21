package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Basket;
import com.training360.yellowcode.dbTables.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BasketsDao {

    private JdbcTemplate jdbcTemplate;

    public BasketsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToBasket(long userId, long productId, long id) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("insert into basket(id,user_id,product_id) values(?,?,?)");
                ps.setLong(1,id);
                ps.setLong(2,userId);
                ps.setLong(3,productId);
                return ps;
            }
        });
    }

    public List<Basket> listProducts(){
        return jdbcTemplate.query("select id, user_id, product_id from basket",
                new BasketsDao.BasketMapper());
    }

    private static class BasketMapper implements RowMapper<Basket> {
        @Override
        public Basket mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long userId = resultSet.getLong("user_id");
            long productId = resultSet.getLong("product_id");
            return new Basket(id,userId,productId);
        }
    }
}