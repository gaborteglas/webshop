package com.training360.yellowcode.dbTables;

import java.util.List;

public class Baskets {

    private List<Basket> products;

    public Baskets() {
    }

    public Baskets(List<Basket> products) {
        this.products = products;
    }

    public List<Basket> getProducts() {
        return products;
    }

    public void setProducts(List<Basket> products) {
        this.products = products;
    }
}
