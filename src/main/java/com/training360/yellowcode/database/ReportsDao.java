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
        return jdbcTemplate.query("select sum(product_price),date,status from orderitem " +
                        "join orders on order_id = orders.id " +
                        "where year(date) = YEAR(CURDATE()) " +
                        "group by month(date)",
                (ResultSet resultSet, int i) -> new Reports(
                        resultSet.getLong("sum(product_price)"),
                        resultSet.getTimestamp("date").toLocalDateTime(),
                        OrderStatus.valueOf(resultSet.getString("status"))
                ));
    }
}
