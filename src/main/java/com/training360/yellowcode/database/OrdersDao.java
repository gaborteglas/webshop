package com.training360.yellowcode.database;

import com.training360.yellowcode.businesslogic.OrdersService;
import com.training360.yellowcode.dbTables.*;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrdersDao {

    private JdbcTemplate jdbcTemplate;

    public OrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Orders> listOrders() {
        List <Orders> allOrders = jdbcTemplate.query("select id, user_id, date, status, quantity, price from orders",
                new OrderMapper());

        for (Orders o: allOrders) {
            setOrderQuantityAndPrice(o.getId());
        }

        return jdbcTemplate.query("select id, user_id, date, status, quantity, price from orders",
                new OrderMapper());
    }

    public List<Orders> listOrdersByUserId(long userId) {
        return jdbcTemplate.query("select id, user_id, date, status, quantity, price from orders where user_id = ?",
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
                "select orderitem.id from orderitem join orders on orderitem.order_id = orders.id " +
                "join products on orderitem.product_id = products.id " +
                "where orders.id = ? and products.address = ?)", orderId, productAddress);
    }

    public long setOrderCount(Orders order) {
        return listOrderItems(order.getUserId(), order.getId()).size();
    }

    public long setOrderPrice(Orders order) {
        List<OrderItem> myOrder = listOrderItems(order.getUserId(), order.getId());
        long totalPrice = 0;
        for (OrderItem o: myOrder) {
            totalPrice += o.getProductPrice();
        }
        return totalPrice;
    }

    public Orders findOrderById(long id) {
        Orders found = jdbcTemplate.queryForObject("select id, user_id, date, status, quantity, price" +
                        " from orders where id=?",
                new OrderMapper(),
                id);
        return found;
    }

    public void setOrderQuantityAndPrice(long id) {
        Orders newOrder = findOrderById(id);

        long count = setOrderCount(newOrder);
        long price = setOrderPrice(newOrder);

        jdbcTemplate.update("update orders set quantity = ?, price = ? where id = ?",
                count, price, id);
    }

    private static class OrderMapper implements RowMapper<Orders> {
        @Override
        public Orders mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long userId = resultSet.getLong("user_id");
            LocalDateTime localDateTime = resultSet.getTimestamp("date").toLocalDateTime();
            OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
            long quantity = resultSet.getLong("quantity");
            long price = resultSet.getLong("price");
            return new Orders(id, userId, localDateTime, status, quantity, price);
        }
    }
}
