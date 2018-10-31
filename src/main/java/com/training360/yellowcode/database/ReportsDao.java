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
        return jdbcTemplate.query("SELECT SUM(product_price * quantity),date,status,quantity " +
                        "FROM orderitem " +
                        "JOIN orders on orderitem.order_id = orders.id " +
                        "WHERE YEAR(date) = YEAR(CURDATE()) " +
                        "GROUP BY mgonth(date),status",
                (ResultSet resultSet, int i) -> new Reports(
                        resultSet.getLong("SUM(product_price * quantity)"),
                        resultSet.getTimestamp("date").toLocalDateTime(),
                        OrderStatus.valueOf(resultSet.getString("status")),
                        resultSet.getLong("quantity")
                ));
    }

    public List<Reports> listReportsByProductAndDate(){
    return jdbcTemplate.query("SELECT products.name,orders.date,quantity " +
                    "FROM orderitem " +
                    "JOIN products on orderitem.product_id = products.id " +
                    "JOIN orders on orderitem.order_id = orders.id " +
                    "WHERE orders.status = 'DELIVERED'" +
                    "GROUP BY month(orders.date), products.name",
            (ResultSet resultSet, int i) -> new Reports(
                        resultSet.getString("products.name"),
                        resultSet.getTimestamp("orders.date").toLocalDateTime(),
                        resultSet.getLong("quantity")
                ));
    }
}
