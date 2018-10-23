package com.training360.yellowcode.dbTables;

import java.text.Normalizer;

public class Product {

    private long id;
    private String name;
    private String address;
    private String producer;
    private long currentPrice;
    private String status;

    public Product() {
    }

    public Product(long id, String name, String producer, long currentPrice) {
        this.id = id;
        this.name = name;
        this.address = userFriendlyAddressGenerator();
        this.producer = producer;
        this.currentPrice = currentPrice;
        this.status = "active";
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String userFriendlyAddressGenerator() {
        String address = name;
        address = address.trim().replaceAll(" ", "-").toLowerCase();
        return Normalizer.normalize(address, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
