package com.training360.yellowcode.dbTables;

public class Basket {

    private long userId;
    private long productId;
    private long id;

    public Basket() {
    }

    public Basket(long userId, long productId, long id) {
        this.userId = userId;
        this.productId = productId;
        this.id = id;
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
