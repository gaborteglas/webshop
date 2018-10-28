package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.OrdersDao;
import com.training360.yellowcode.dbTables.OrderItem;
import com.training360.yellowcode.dbTables.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private OrdersDao ordersDao;
    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public OrdersService(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Orders> listOrders() {
        return sortOrdersByDate(ordersDao.listOrders());
    }

    public List<Orders> listOrdersByUserId(long userId) {
        return sortOrdersByDate(ordersDao.listOrdersByUserId(userId));
    }

    public List<OrderItem> listOrderItems (long userId, long orderId) {
        return ordersDao.listOrderItems(userId, orderId);
    }

    public void createOrderAndOrderItems(long userId) {
        ordersDao.createOrderAndOrderItems(userId);
        LOGGER.info(MessageFormat.format("Order created(user_id: {0}, date: {1}, status: {2})",
                userId, LocalDateTime.now(), "ACTIVE"));
    }

    private List<Orders> sortOrdersByDate(List<Orders> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Orders::getDate))
                .collect(Collectors.toList());
    }
}
