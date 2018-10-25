package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;

public class Orders {

    private long id;
    private long userId;
    private LocalDateTime date;
    private OrderStatus status;

    public Orders(long id, long userId, LocalDateTime date, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
    }

    public Orders(long userId) {
        this.userId = userId;
    }

    public Orders() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
