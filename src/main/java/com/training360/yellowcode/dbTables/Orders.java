package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;
import java.util.List;

public class Orders {

    private List<OrderItem> orders;
    private long id;
    private String userId;
    private LocalDateTime date;
    private OrderStatus status;

    public Orders(long id, String userId, LocalDateTime date, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
