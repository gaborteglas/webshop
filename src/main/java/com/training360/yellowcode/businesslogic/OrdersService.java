package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.OrdersDao;
import com.training360.yellowcode.dbTables.Orders;

import java.util.List;

public class OrdersService {

    private OrdersDao ordersDao;

    public OrdersService(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    public List<Orders> listOrders() {
        return ordersDao.listOrders();
    }

    public List<Orders> listActiveOrdersForUser(long userId) {
        return ordersDao.listActiveOrdersForUser(userId);
    }
}
