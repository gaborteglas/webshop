package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.OrderItem;
import com.training360.yellowcode.userinterface.OrderItemController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearorderitem.sql")
@WithMockUser(username = "testadmin", roles = "ADMIN")
public class YellowCodeApplicationOrderItemTest {



        @Autowired
        private OrderItemController orderItemController;

        @Before
        public void init() {
            orderItemController.addToOrderItems(new OrderItem(1, 1, 3, 600));
            orderItemController.addToOrderItems(new OrderItem(2, 1, 6, 500));
            orderItemController.addToOrderItems(new OrderItem(3, 2, 9, 400));
        }

        @Test
        public void testListOrderItems() {
            List<OrderItem> orderItems = orderItemController.listOrders(1);

            assertEquals(orderItems.size(), 2);
        }

        @Test
        public void testAddOrderItems() {
            orderItemController.addToOrderItems(new OrderItem(4, 2, 11, 100));
            List<OrderItem> orderItems = orderItemController.listOrders(2);

            assertEquals(orderItems.size(), 2);
        }

        @Test
    public void testAddMultipleOrderItem() {
            List<OrderItem> orderItemsToAdd = new ArrayList<>();
            orderItemsToAdd.add(new OrderItem(5, 1, 5, 200));
            orderItemsToAdd.add(new OrderItem(6, 1, 8, 140));

            orderItemController.addMultipleOrderItems(orderItemsToAdd);
            List<OrderItem> orderItems = orderItemController.listOrders(1);

            assertEquals(orderItems.size(), 4);
        }
}
