package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.OrderItemDao;
import com.training360.yellowcode.dbTables.OrderItem;

import java.util.List;

public class OrderItemService {

    private OrderItemDao orderItemDao;

    public OrderItemService(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public List<OrderItem> listOrderItems() {
        return orderItemDao.listOrderItems();
    }

    public List<OrderItem> listOrderItemsForOrder(long orderId) {
        return orderItemDao.listOrderItemsForOrder(orderId);
    }
}