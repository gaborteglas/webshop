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

    public List<OrderItem> listOrderItems(long userId, long orderId) {
        return ordersDao.listOrderItems(userId, orderId);
    }

    public void createOrderAndOrderItems(long userId) {
        ordersDao.createOrderAndOrderItems(userId);
        List<Orders> orders = listOrdersByUserId(userId);
        List<OrderItem> orderItems = listOrderItems(userId, orders.get(orders.size() - 1).getId());
        LOGGER.info(MessageFormat.format("Order created(id: {0}, userId: {1}, date: {2}, status: {3})",
                orders.get(orders.size() - 1).getId(), userId, LocalDateTime.now(), "ACTIVE"));
        for (OrderItem orderItem : orderItems) {
            LOGGER.info(MessageFormat.format("OrderItem added to order(id: {0}, orderId: {1}, productId: {2}, " +
                            "productName: {3}, producer: {4}, productPrice: {5})",
                    orderItem.getId(), orderItem.getOrderId(), orderItem.getProductId(),
                    orderItem.getProductName(), orderItem.getProducer(), orderItem.getProductPrice()));
        }
    }

    private List<Orders> sortOrdersByDate(List<Orders> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Orders::getDate))
                .collect(Collectors.toList());
    }
}
