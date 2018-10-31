package com.training360.yellowcode.dbTables;

import java.util.List;

public class Product {

    private long id;
    private String name;
    private String address;
    private String producer;
    private long currentPrice;
    private ProductStatusType status = ProductStatusType.ACTIVE;
    private Category category;
    private List<Feedback> feedbacks;

    public Product() {
    }

    public Product(long id, String name, String address, String producer, long currentPrice, ProductStatusType status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.producer = producer;
        this.currentPrice = currentPrice;
        this.status = status;
    }

    public Product(long id, String name, String address, String producer, long currentPrice, ProductStatusType status, Category category) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.producer = producer;
        this.currentPrice = currentPrice;
        this.status = status;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public ProductStatusType getStatus() {
        return status;
    }

    public void setStatus(ProductStatusType status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
