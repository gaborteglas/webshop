package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.OrdersService;
import com.training360.yellowcode.dbTables.Orders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportsController {

    OrdersService ordersService;

    public ReportsController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @RequestMapping(value = "/api/reports/orders", method = RequestMethod.GET)
    public List<Orders> listOrders() {
        return ordersService.listOrders();
    }


}
