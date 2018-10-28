package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;

public class Orders {

    private long id;
    private long userId;
    private LocalDateTime date;
    private OrderStatus status;
    private long quantity;
    private long price;

    public Orders(long id, long userId, LocalDateTime date, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
    }

    public Orders(long id, long userId, LocalDateTime date, OrderStatus status, long quantity, long price) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
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

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
