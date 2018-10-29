package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;

public class Reports {
    private long totalPrice;
    private LocalDateTime date;
    private OrderStatus status;

    public Reports(long totalPrice, LocalDateTime date, OrderStatus status) {
        this.totalPrice = totalPrice;
        this.date = date;
        this.status = status;
    }

    public Reports() {
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
