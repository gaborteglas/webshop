package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.*;
import com.training360.yellowcode.userinterface.BasketController;
import com.training360.yellowcode.userinterface.OrdersController;
import com.training360.yellowcode.userinterface.ProductController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearorders.sql")
@WithMockUser(username = "testuser", roles = "USER")
public class YellowCodeApplicationOrdersTest {

    @Autowired
    private OrdersController ordersController;

    @Autowired
    private BasketController basketController;

    @Autowired
    private ProductController productController;


    @Before
    public void init() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testadmin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        productController.createProduct(new Product(1, "Az aliceblue 50 árnyalata", "aliceblue", "E. L. Doe", 9999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(2, "Legendás programozók és megfigyelésük", "legendas", "J. K. Doe", 3999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(3, "Az 50 első Trainer osztály", "osztaly", "Jack Doe", 5999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(4, "Hogyan neveld a junior fejlesztődet", "junior", "Jane Doe", 6499, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(5, "Junior most és mindörökké", "mindorokke", "James Doe", 2999, ProductStatusType.ACTIVE));

        SecurityContextHolder.getContext().setAuthentication(a);

        basketController.addToBasket(1);
    }

    @Test
    public void testListOrdersByUserIdWithoutExistingOrder() {
        List<Orders> ordersList = ordersController.listOrdersByUserId();

        assertEquals(ordersList.size(), 0);
    }

    @Test
    public void testListOrdersByUserId() {
        ordersController.createOrderAndOrderItems();
        List<Orders> ordersList1 = ordersController.listOrdersByUserId();
        assertEquals(ordersList1.size(), 1);

        basketController.addToBasket(2);
        ordersController.createOrderAndOrderItems();
        List<Orders> ordersList2 = ordersController.listOrdersByUserId();
        assertEquals(ordersList2.size(), 2);
    }

    @Test
    public void testListOrderItems() {
        basketController.addToBasket(2);
        ordersController.createOrderAndOrderItems();

        List<OrderItem> orderItemList = ordersController.listOrderItems(1);
        assertEquals(orderItemList.size(), 2);
    }

    @Test
    public void testOrderItemDatas() {
        ordersController.createOrderAndOrderItems();

        List<OrderItem> orderItemList = ordersController.listOrderItems(1);
        assertEquals(orderItemList.get(0).getProductName(), "Az aliceblue 50 árnyalata");
        assertEquals(orderItemList.get(0).getProductPrice(), 9999);
    }

    @Test
    public void testEmptyBasketAfterOrder() {
        basketController.addToBasket(2);
        basketController.addToBasket(3);

        ordersController.createOrderAndOrderItems();
        List<Orders> ordersList = ordersController.listOrdersByUserId();
        List<OrderItem> orderItemList = ordersController.listOrderItems(1);

        assertEquals(ordersList.size(), 1);
        assertEquals(orderItemList.size(), 3);

        List<Product> basketItems = basketController.listProducts();
        assertEquals(basketItems.size(), 0);
    }

}
