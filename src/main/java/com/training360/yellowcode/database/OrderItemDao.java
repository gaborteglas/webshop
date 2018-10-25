package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDao {

    private JdbcTemplate jdbcTemplate;

    public OrderItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrderItem> listOrderItems() {
        return
                jdbcTemplate.query("select id, order_id, product_id, product_price from orderitem",
                        new OrderItemMapper()
                );
    }

    private static class OrderItemMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long orderId = resultSet.getLong("order_id");
            long productId = resultSet.getLong("product_id");
            long productPrice = resultSet.getLong("product_price");
            return new OrderItem(id, orderId, productId, productPrice);
        }
    }
}
