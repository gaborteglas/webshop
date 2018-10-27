package com.training360.yellowcode.database;

import com.training360.yellowcode.businesslogic.OrdersService;
import com.training360.yellowcode.dbTables.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrdersDao {

    private JdbcTemplate jdbcTemplate;

    public OrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Orders> listOrders(long userId) {
        return jdbcTemplate.query("select id, user_id, date, status from orders where user_id = ?",
                new OrderMapper(),
                userId);
    }

    public List<OrderItem> listOrderItems(long userId, long orderId) {
        return jdbcTemplate.query(
                "select orderitem.id, orderitem.order_id, orderitem.product_id, " +
                        "orderitem.product_price, product.name from orderitem " +
                        "join order on orderitem.order_id = order.id " +
                        "join product on orderitem.product_id = product.id " +
                        "where order.id = ? and order.user_id = ?",
                (ResultSet resultSet, int i) -> new OrderItem(
                        resultSet.getLong("orderitem.id"),
                        resultSet.getLong("orderitem.order_id"),
                        resultSet.getLong("orderitem.product_id"),
                        resultSet.getString("product.name"),
                        resultSet.getLong("orderitem.product_price")),
                orderId, userId);
    }

    public void createOrderAndOrderItems(long userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into orders(user_id, date, status) values(?, ?, 'ACTIVE')",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userId);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);
        long orderId = keyHolder.getKey().longValue();

        jdbcTemplate.update("insert into orderitem (order_id, product_id, product_price) " +
                "select ?, products.id, products.price from products " +
                "inner join basket on products.id = basket.product_id " +
                "where basket.user_id = ?", orderId, userId);

        jdbcTemplate.update("delete from basket where user_id = ?", userId);
    }

    private static class OrderMapper implements RowMapper<Orders> {
        @Override
        public Orders mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long userId = resultSet.getLong("user_id");
            LocalDateTime localDateTime = resultSet.getTimestamp("date").toLocalDateTime();
            OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
            return new Orders(id, userId, localDateTime, status);
        }
    }
}
