package com.training360.yellowcode.dbTables;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

public class OrderItem {

    private long id;
    private String userName;
    private long productId;
    private LocalDateTime orderDate;
    private OrderType status;

}
