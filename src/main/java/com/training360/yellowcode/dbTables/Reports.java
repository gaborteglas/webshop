package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;
import java.time.Month;


public class Reports {
    private long totalPrice;
    private Month date;
    private OrderStatus status;

    public Reports(long totalPrice, LocalDateTime date, OrderStatus status) {
        this.totalPrice = totalPrice;
        this.date = date.getMonth();
        this.status = status;
    }

    public Reports(long totalPrice, Month date) {
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public Reports() {
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Month getDate() {
        return date;
    }

    public void setDate(LocalDateTime month) {
        this.date = month.getMonth();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
