package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.OrderStatus;
import com.training360.yellowcode.dbTables.Orders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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
