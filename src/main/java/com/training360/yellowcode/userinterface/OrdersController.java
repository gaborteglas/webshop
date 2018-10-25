package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.OrdersService;
import com.training360.yellowcode.dbTables.Orders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

public class OrdersController {

    private OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @RequestMapping(value = "/api/myorders", method = RequestMethod.GET)
    public List<Orders> listOrders() {
        return ordersService.listOrders();
    }
}
