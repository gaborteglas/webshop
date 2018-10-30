package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class Reports {
    private long totalPrice;
    private String date;
    private OrderStatus status;
    private String productName;
    private long productCount;

    public Reports(long totalPrice, LocalDateTime date, OrderStatus status,long productCount) {
        this.totalPrice = totalPrice;
        this.date = date.getMonth().getDisplayName(TextStyle.FULL,new Locale("HU"));
        this.status = status;
        this.productCount = productCount;
    }

    public Reports(String productName,LocalDateTime date,long productCount) {
        this.date = date.getMonth().getDisplayName(TextStyle.FULL,new Locale("HU"));
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

    public String getDate() {
        return date;
    }

    public void setDate(LocalDateTime month) {
        this.date = month.getMonth().getDisplayName(TextStyle.FULL,new Locale("HU"));
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

}
