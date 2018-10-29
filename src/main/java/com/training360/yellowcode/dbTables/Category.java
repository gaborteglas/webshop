package com.training360.yellowcode.dbTables;

public class Category {
    private long id;
    private String name;
    private long positionNumber;

    public Category(long id, String name, long positionNumber) {
        this.id = id;
        this.name = name;
        this.positionNumber = positionNumber;
    }

    public Category() {
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

    public long getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(long positionNumber) {
        this.positionNumber = positionNumber;
    }
}