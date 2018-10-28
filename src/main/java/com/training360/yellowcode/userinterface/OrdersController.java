package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.OrdersService;
import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.businesslogic.UserService;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.OrderItem;
import com.training360.yellowcode.dbTables.Orders;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrdersController {

    private OrdersService ordersService;
    private UserService userService;

    public OrdersController(OrdersService ordersService, UserService userService) {
        this.ordersService = ordersService;
        this.userService = userService;
    }

    @RequestMapping(value = "/api/orders", method = RequestMethod.GET)
    public List<Orders> listOrders() {
        return ordersService.listOrders();
    }

    @RequestMapping(value = "/api/orders/{orderId}", method = RequestMethod.GET)
    public List<OrderItem> listOrderItemsForAdmin(@PathVariable long orderId) {
        return ordersService.listOrderItemsForAdmin(orderId);
    }

    @RequestMapping(value = "/api/myorders", method = RequestMethod.GET)
    public List<Orders> listOrdersByUserId() {
        User user = getAuthenticatedUserId();
        if (user != null) {
            return ordersService.listOrdersByUserId(user.getId());
        } else {
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/api/myorderitems/{orderId}", method = RequestMethod.GET)
    public List<OrderItem> listOrderItems(@PathVariable long orderId) {
        User user = getAuthenticatedUserId();
        if (user != null) {
            return ordersService.listOrderItems(user.getId(), orderId);
        } else {
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/api/myorders", method = RequestMethod.POST)
    public Response createOrderAndOrderItems() {
        User user = getAuthenticatedUserId();
        if (user != null) {
            try {
                ordersService.createOrderAndOrderItems(user.getId());
                return new Response(true, "Sikeres rendelés.");
            } catch (IllegalStateException ise) {
                return new Response(false, "A kosár üres");
            }
        } else {
            return new Response(false, "A felhasználó nincs bejelentkezve.");
        }
    }

    @RequestMapping(value = "/api/orders/{orderId}", method = RequestMethod.DELETE)
    public void deleteOrder(@PathVariable long orderId) {
        ordersService.deleteOrder(orderId);
    }

    @RequestMapping(value = "/api/orders/{orderId}/{productAddress}", method = RequestMethod.DELETE)
    public void deleteOrderItem(@PathVariable long orderId, @PathVariable String productAddress) {
        ordersService.deleteOrderItem(orderId, productAddress);
    }

    private User getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {     //nincs bejelentkezve
            return null;
        }
        User user = userService.findUserByUserName(authentication.getName()).get();
        return user;
    }

}
