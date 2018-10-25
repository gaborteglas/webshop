package com.training360.yellowcode.database;

import com.training360.yellowcode.businesslogic.OrderItemService;
import com.training360.yellowcode.dbTables.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
@Repository
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

    public List<OrderItem> listOrderItemsForOrder(long orderId) {
        return
                jdbcTemplate.query("select id, order_id, product_id, product_price from orderitem where order_id = ?",
                        new OrderItemMapper(), orderId);
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

    public void addToOrderItems(OrderItem orderItem) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into orderitem(order_id, product_id, product_price) values(?, ?, ?)"
            );
            ps.setLong(1, orderItem.getOrderId());
            ps.setLong(2, orderItem.getProductId());
            ps.setLong(3, orderItem.getProductPrice());
            return ps;
        });
        OrderItemService.LOGGER.info("Item added to order(order_id: {0}, product_id: {1}, product_price: {2})",
                orderItem.getOrderId(), orderItem.getProductId(), orderItem.getProductPrice());
    }

    public void addMultipleOrderItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into orderitem(order_id, product_id, product_price) values(?, ?, ?)"
                );
                ps.setLong(1, orderItem.getOrderId());
                ps.setLong(2, orderItem.getProductId());
                ps.setLong(3, orderItem.getProductPrice());
                return ps;
            });
            OrderItemService.LOGGER.info("Item added to order(order_id: {0}, product_id: {1}, product_price: {2})",
                    orderItem.getOrderId(), orderItem.getProductId(), orderItem.getProductPrice());
        }
    }
}
