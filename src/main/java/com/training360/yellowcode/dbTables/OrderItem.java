package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;

public class OrderItem {

    private long id;
    private long orderId;
    private long productId;
    private String productAddress;

    public OrderItem(long id, long orderId, long productId,String productAddress) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productAddress = productAddress;
    }

    public OrderItem(long orderId, long productId,String productAddress) {
        this.orderId = orderId;
        this.productId = productId;
        this.productAddress = productAddress;
    }

    public OrderItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    public String getProductAddress() {
        return productAddress;
    }

    public void setProductAddress(String productAddress) {
        this.productAddress = productAddress;
    }
}
