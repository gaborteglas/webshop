package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.OrdersDao;
import com.training360.yellowcode.dbTables.Orders;

import java.util.List;

public class OrdersService {

    private OrdersDao ordersDao;

    public List<Orders> listOrders() {
        return ordersDao.listOrders();
    }
}
