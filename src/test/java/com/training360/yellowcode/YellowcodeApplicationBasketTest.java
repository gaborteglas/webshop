package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.*;
import com.training360.yellowcode.userinterface.BasketController;
import com.training360.yellowcode.userinterface.ProductController;
import com.training360.yellowcode.userinterface.UserController;
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
@Sql(scripts = "classpath:/clearbaskets.sql")
public class YellowcodeApplicationBasketTest {

    @Autowired
    private BasketController basketController;

    @Autowired
    private ProductController productController;

    @Autowired
    private UserController userController;

    @Before
    public void init() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testadmin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        productController.createProduct(new Product(1, "Az aliceblue 50 árnyalata", "aliceblue", "E. L. Doe", 9999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(2, "Legendás programozók és megfigyelésük", "legendas", "J. K. Doe", 3999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(3, "Az 50 első Trainer osztály", "osztaly", "Jack Doe", 5999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(4, "Hogyan neveld a junior fejlesztődet", "junior", "Jane Doe", 6499, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(5, "Junior most és mindörökké", "mindorokke", "James Doe", 2999, ProductStatusType.ACTIVE));

        userController.createUser(new User(1, "admin1", "Test One", "Elsőjelszó1", UserRole.ROLE_ADMIN));
        userController.createUser(new User(2, "user1", "Test Two", "Másodikjelszó2", UserRole.ROLE_CUSTOMER));
        userController.createUser(new User(3, "user2", "Test Three", "harmadikJelszó3",UserRole.ROLE_CUSTOMER));

        SecurityContextHolder.getContext().setAuthentication(a);
    }

    @Test
    @WithMockUser(username = "user1", roles = "CUSTOMER")
    public void testBasketDatas() {
        Basket basket = new Basket(1, 2, 5);
        assertEquals(basket.getId(), 1);
        assertEquals(basket.getUserId(), 2);
        assertEquals(basket.getProductId(), 5);
    }

    @Test
    @WithMockUser(username = "admin1", roles = "ADMIN")
    public void testListBasketProductsWithEmpty() {
        List<Basket> basketItems = basketController.listProducts();
        assertEquals(basketItems.size(), 0);
    }

    @WithMockUser(username = "user1", roles = "CUSTOMER")
    @Test
    public void testAddToBasketThenListBasketProducts() {
        basketController.addToBasket(new Basket(2, 4));

        List<Basket> basketItems = basketController.listProducts();
        assertEquals(basketItems.size(), 1);
    }

    @WithMockUser(username = "user1", roles = "CUSTOMER")
    @Test
    public void testAddProductsMultipleTimesToBasket() {
        basketController.addToBasket(new Basket(2, 1));
        basketController.addToBasket(new Basket(2, 1));
        basketController.addToBasket(new Basket(2, 1));

        List<Basket> basketItems = basketController.listProducts();
        assertEquals(basketItems.size(), 1);
    }

    @Test
    @WithMockUser(username = "admin1", roles = "ADMIN")
    public void testDeleteFromBasketByUserId() {
        basketController.addToBasket(new Basket(1, 1));
        basketController.addToBasket(new Basket(1, 2));


        List<Basket> basketItems1 = basketController.listProducts();
        assertEquals(basketItems1.size(), 2);

        basketController.deleteFromBasketByUserId(1);
        List<Basket> basketItems2 = basketController.listProducts();
        assertEquals(basketItems2.size(), 0);

    }

    @Test
    @WithMockUser(username = "user2", roles = "CUSTOMER")
    public void testDeleteFromBasketByProductIdAndUserId() {
        basketController.addToBasket(new Basket(3, 1));
        basketController.addToBasket(new Basket(3, 4));
        basketController.addToBasket(new Basket(3, 5));

        List<Basket> basketItems1 = basketController.listProducts();
        assertEquals(basketItems1.size(), 3);

        basketController.deleteFromBasketByProductIdAndUserId(3, 1);
        List<Basket> basketItems2 = basketController.listProducts();
        assertEquals(basketItems2.size(), 2);

    }

}
