package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.OrderItemService;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "/api/myorderitems", method = RequestMethod.POST)
    public ResponseEntity<String> addToOrderItems(@RequestBody OrderItem orderItem) {
        try {
            orderItemService.addToOrderItems(orderItem);
            return ResponseEntity.ok("Successfully created.");
        } catch (DuplicateProductException dpe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(value = "/api/myorderitems2", method = RequestMethod.POST)
    public ResponseEntity<String> addMultipleOrderItems(@RequestBody List<OrderItem> orderItems) {
        try {
            orderItemService.addMultipleOrderItems(orderItems);
            return ResponseEntity.ok("Successfully created.");
        } catch (DuplicateProductException dpe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
