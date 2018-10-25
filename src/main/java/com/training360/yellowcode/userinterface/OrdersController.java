package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.OrdersService;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.Orders;
import com.training360.yellowcode.dbTables.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "/api/myorders", method = RequestMethod.POST)
    public ResponseEntity<String> createOrders(@RequestBody long userId) {
        try {
            ordersService.createOrders(userId);
            return ResponseEntity.ok("Successfully created.");
        } catch (DuplicateProductException dpe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
