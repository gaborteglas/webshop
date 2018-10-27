package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Basket;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.ProductStatusType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
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

    public List<Basket> findBasketByByUserIdAndProductId(Basket basket) {
        return jdbcTemplate.query("select id, user_id, product_id from basket where user_id = ? and product_id = ?",
                new BasketMapper(),
                basket.getUserId(),
                basket.getProductId());
    }

    public void addToBasket(Basket basket) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into basket(user_id,product_id) values(?,?)");
            ps.setLong(1, basket.getUserId());
            ps.setLong(2, basket.getProductId());
            return ps;
        });
    }

    public List<Product> listProducts(long userId) {
        return jdbcTemplate.query(
                "select products.id, products.name, products.address, products.producer, products.price from products " +
                        "inner join basket on products.id = basket.product_id " +
                        "where basket.user_id = ? and products.status = 'ACTIVE'",
                (ResultSet resultSet, int i) ->
                        new Product(resultSet.getLong("products.id"),
                                    resultSet.getString("products.name"),
                                    resultSet.getString("products.address"),
                                    resultSet.getString("products.producer"),
                                    resultSet.getLong("products.price"),
                                    ProductStatusType.ACTIVE),
                userId);
    }

    private static class BasketMapper implements RowMapper<Basket> {
        @Override
        public Basket mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long userId = resultSet.getLong("user_id");
            long productId = resultSet.getLong("product_id");
            Basket basket = new Basket(id, userId, productId);
            return basket;
        }
    }

    public void deleteFromBasketByUserId(long userId) {
        jdbcTemplate.update("delete from basket where user_id = ?", userId);
    }

    public void deleteFromBasketByProductIdAndUserId(long userId, long productId) {
        jdbcTemplate.update("delete from basket where user_id = ? AND product_id = ?", userId, productId);
    }
}