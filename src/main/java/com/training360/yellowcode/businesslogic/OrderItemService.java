package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.OrderItemDao;
import com.training360.yellowcode.dbTables.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderItemService {

    private OrderItemDao orderItemDao;
    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public OrderItemService(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public List<OrderItem> listOrderItems() {
        return orderItemDao.listOrderItems();
    }

    public List<OrderItem> listOrderItemsForOrder(long orderId) {
        return orderItemDao.listOrderItemsForOrder(orderId);
    }

    public void addToOrderItems(OrderItem orderItem) {
        orderItemDao.addToOrderItems(orderItem);
    }

    public void addMultipleOrderItems(List<OrderItem> orderItems) {
        orderItemDao.addMultipleOrderItems(orderItems);
    }
}
