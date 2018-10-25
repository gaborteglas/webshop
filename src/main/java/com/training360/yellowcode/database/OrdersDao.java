package com.training360.yellowcode.database;

import com.training360.yellowcode.businesslogic.OrdersService;
import com.training360.yellowcode.dbTables.OrderStatus;
import com.training360.yellowcode.dbTables.Orders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
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

    public List<Orders> listOrders() {
        return
                jdbcTemplate.query("select id, user_id, date, status from orders",
                        new OrderMapper()
                );
    }

    public List<Orders> listActiveOrdersForUser(long userId) {
        return sortOrdersByDate(jdbcTemplate.query("select id, user_id, date, status from orders where user_id = ? and status = 'ACTIVE'",
                new OrderMapper(),
                userId
        ));
    }

    private List<Orders> sortOrdersByDate(List<Orders> orders) {
        return orders.stream().sorted(Comparator.comparing(Orders::getDate)).collect(Collectors.toList());
    }

    public void createOrders(long userId) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into orders(user_id, date, status) values(?, ?, 'ACTIVE')"
            );
            ps.setLong(1, userId);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        });
        OrdersService.LOGGER.info(MessageFormat.format("Order created(user_id: {0}, date: {1}, status: {2})",
                userId, LocalDateTime.now(), "ACTIVE")
        );
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
