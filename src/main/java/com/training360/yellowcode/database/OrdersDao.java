package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrdersDao {

    private JdbcTemplate jdbcTemplate;

    public OrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Orders> listOrders() {
        return jdbcTemplate.query("select orders.id, orders.user_id, orders.date, orders.status, " +
                        "count(*) as quantity, sum(orderitem.product_price) as price from orders " +
                        "join orderitem on orders.id = orderitem.order_id " +
                        "group by orders.id " +
                        "order by orders.date desc",
                (ResultSet resultSet, int i) -> new Orders(
                        resultSet.getLong("orders.id"),
                        resultSet.getLong("orders.user_id"),
                        resultSet.getTimestamp("orders.date").toLocalDateTime(),
                        OrderStatus.valueOf(resultSet.getString("orders.status")),
                        resultSet.getLong("quantity"),
                        resultSet.getLong("price")
                ));
    }

    public List<Orders> listOrdersByUserId(long userId) {
        return jdbcTemplate.query("select id, user_id, date, status from orders where user_id = ? " +
                        "order by date desc",
                new OrderMapper(),
                userId);
    }

    public List<OrderItem> listOrderItems(long userId, long orderId) {
        return jdbcTemplate.query(
                "select orderitem.id, orderitem.order_id, orderitem.product_id, " +
                        "orderitem.product_price, products.name, products.producer, products.address from orderitem " +
                        "join orders on orderitem.order_id = orders.id " +
                        "join products on orderitem.product_id = products.id " +
                        "where orders.id = ? and orders.user_id = ?",
                (ResultSet resultSet, int i) -> new OrderItem(
                        resultSet.getLong("orderitem.id"),
                        resultSet.getLong("orderitem.order_id"),
                        resultSet.getLong("orderitem.product_id"),
                        resultSet.getString("products.name"),
                        resultSet.getString("products.address"),
                        resultSet.getString("products.producer"),
                        resultSet.getLong("orderitem.product_price")),
                orderId, userId);
    }

    public List<OrderItem> listOrderItemsForAdmin(long orderId) {
        return jdbcTemplate.query(
                "select orderitem.id, orderitem.order_id, orderitem.product_id, " +
                        "orderitem.product_price, products.name, products.producer, products.address from orderitem " +
                        "join orders on orderitem.order_id = orders.id " +
                        "join products on orderitem.product_id = products.id " +
                        "where orders.id = ?",
                (ResultSet resultSet, int i) -> new OrderItem(
                        resultSet.getLong("orderitem.id"),
                        resultSet.getLong("orderitem.order_id"),
                        resultSet.getLong("orderitem.product_id"),
                        resultSet.getString("products.name"),
                        resultSet.getString("products.address"),
                        resultSet.getString("products.producer"),
                        resultSet.getLong("orderitem.product_price")),
                orderId);
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

    public void deleteOrder(long orderId) {
        jdbcTemplate.update("update orders set status = 'DELETED' where id = ?", orderId);
    }

    public void deleteOrderItem(long orderId, String productAddress) {
        jdbcTemplate.update("delete from orderitem where id in (" +
                "select orderitems.id from (select * from orderitem) as orderitems " +
                "join orders on orderitems.order_id = orders.id " +
                "join products on orderitems.product_id = products.id " +
                "where orders.id = ? and products.address = ?)", orderId, productAddress);
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
