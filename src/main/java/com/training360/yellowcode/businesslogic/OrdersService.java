package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.OrdersDao;
import com.training360.yellowcode.dbTables.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrdersService {

    private OrdersDao ordersDao;
    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public OrdersService(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    public List<Orders> listOrders() {
        return ordersDao.listOrders();
    }

    public List<Orders> listActiveOrdersForUser(long userId) {
        return ordersDao.listActiveOrdersForUser(userId);
    }

    public void createOrders(long userId) {
        ordersDao.createOrders(userId);
    }
}
