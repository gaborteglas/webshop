package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.Orders;
import com.training360.yellowcode.userinterface.OrdersController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/orders.sql")
@WithMockUser(username = "testadmin", roles = "ADMIN")
public class YellowCodeApplicationOrdersTest {

    @Autowired
    private OrdersController ordersController;

    @Before
    public void init() {
        ordersController.createOrders(1);
        ordersController.createOrders(2);
    }

    @Test
    public void testListOrders() {
        List<Orders> ordersList = ordersController.listOrders();

        assertEquals(ordersList.size(), 2);
    }

    @Test
    public void testCreateOrders() {
        ordersController.createOrders(3);
        List<Orders> ordersList = ordersController.listOrders();

        assertEquals(ordersList.size(), 3);
    }

}