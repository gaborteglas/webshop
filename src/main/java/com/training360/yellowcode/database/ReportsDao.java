package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Basket;
import com.training360.yellowcode.dbTables.OrderStatus;
import com.training360.yellowcode.dbTables.Orders;
import com.training360.yellowcode.dbTables.Reports;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class ReportsDao {

    private JdbcTemplate jdbcTemplate;

    public ReportsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reports> listReportsByDate() {
        return jdbcTemplate.query("SELECT DISTINCT SUM(product_price),date,status " +
                        "FROM orderitem " +
                        "JOIN orders on orderitem.order_id = orders.id " +
                        "GROUP BY month(date),status",
                (ResultSet resultSet, int i) -> new Reports(
                        resultSet.getLong("SUM(product_price)"),
                        resultSet.getTimestamp("date").toLocalDateTime(),
                        OrderStatus.valueOf(resultSet.getString("status"))
                ));
    }
}
