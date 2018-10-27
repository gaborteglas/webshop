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
            orderItemController.addToOrderItems(new OrderItem(1,2,3,"az-50-elso-trainer-osztaly"));
            orderItemController.addToOrderItems(new OrderItem(1, 1, 3,"az-50-elso-trainer-osztaly"));
            orderItemController.addToOrderItems(new OrderItem(2, 1, 6,"a-java-ura-a-classok-szovetsege"));
            orderItemController.addToOrderItems(new OrderItem(3, 2, 9,"nemzeti-java"));
        }

        @Test
        public void testListOrderItems() {
            List<OrderItem> orderItems = orderItemController.listOrders(1);

            assertEquals(orderItems.size(), 2);
        }

        @Test
        public void testAddOrderItems() {
            orderItemController.addToOrderItems(new OrderItem(4, 2, 11,"egy-kis-stackoverflow"));
            List<OrderItem> orderItems = orderItemController.listOrders(2);

            assertEquals(orderItems.size(), 2);
        }

        @Test
    public void testAddMultipleOrderItem() {
            List<OrderItem> orderItemsToAdd = new ArrayList<>();
            orderItemsToAdd.add(new OrderItem(5, 1, 5,"junior-most-es-mindorokke"));
            orderItemsToAdd.add(new OrderItem(6, 1, 8,"junioroskert"));

            orderItemController.addMultipleOrderItems(orderItemsToAdd);
            List<OrderItem> orderItems = orderItemController.listOrders(1);

            assertEquals(orderItems.size(), 4);
        }
}
