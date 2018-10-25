package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.OrderItemService;
import com.training360.yellowcode.dbTables.OrderItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class OrderItemController {

    private OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @RequestMapping(value = "/api/myorderitems", method = RequestMethod.GET)
    public List<OrderItem> listOrders(@PathVariable long orderId) {
        return orderItemService.listOrderItemsForOrder(orderId);
    }
}
