package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;
import java.time.Month;


public class Reports {
    private long totalPrice;
    private Month date;
    private OrderStatus status;
    private String productName;
    private long productCount;
    private long month;

    public Reports(long totalPrice, LocalDateTime date, OrderStatus status) {
        this.totalPrice = totalPrice;
        this.date = date.getMonth();
        this.status = status;
    }

    public Reports(String productName,long month,long productCount) {
        this.month = month;
        this.productName = productName;
        this.productCount = productCount;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }
}
