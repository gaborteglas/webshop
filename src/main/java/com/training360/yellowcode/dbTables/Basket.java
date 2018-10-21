package com.training360.yellowcode.dbTables;

public class Basket {

    private long id;
    private long userId;
    private long productId;

    public Basket() {
    }

    public Basket(long id,long userId, long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
