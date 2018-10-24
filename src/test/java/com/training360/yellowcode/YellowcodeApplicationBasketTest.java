package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.Basket;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.ProductStatusType;
import com.training360.yellowcode.userinterface.BasketController;
import com.training360.yellowcode.userinterface.ProductController;
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
@Sql(scripts = "classpath:/clearbaskets.sql")
@WithMockUser(username = "testadmin", roles = "ADMIN")
public class YellowcodeApplicationBasketTest {

    @Autowired
    private BasketController basketController;

    @Autowired
    private ProductController productController;

    @Before
    public void init() {
        productController.createProduct(new Product(1, "Az aliceblue 50 árnyalata", "aliceblue", "E. L. Doe", 9999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(2, "Legendás programozók és megfigyelésük", "legendas", "J. K. Doe", 3999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(3, "Az 50 első Trainer osztály", "osztaly", "Jack Doe", 5999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(4, "Hogyan neveld a junior fejlesztődet", "junior", "Jane Doe", 6499, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(5, "Junior most és mindörökké", "mindorokke", "James Doe", 2999, ProductStatusType.ACTIVE));
    }

    @Test
    public void testBasketDatas() {
        Basket basket = new Basket(1, 1, 5);
        assertEquals(basket.getId(), 1);
        assertEquals(basket.getUserId(), 1);
        assertEquals(basket.getProductId(), 5);
    }

    @Test
    public void testListBasketProductsWithEmpty() {
        List<Basket> basketItems = basketController.listProducts();
        assertEquals(basketItems.size(), 0);
    }

    @Test
    public void testAddToBasketThenListBasketProducts() {
        basketController.addToBasket(new Basket(1, 1));
        basketController.addToBasket(new Basket(1, 2));
        basketController.addToBasket(new Basket(3, 4));

        List<Basket> basketItems = basketController.listProducts();
        assertEquals(basketItems.size(), 3);
    }

    @Test
    public void testDeleteFromBasketById() {
        basketController.addToBasket(new Basket(1, 1));
        basketController.addToBasket(new Basket(1, 2));
        basketController.addToBasket(new Basket(2, 1));

        List<Basket> basketItems1 = basketController.listProducts();
        assertEquals(basketItems1.size(), 3);

        basketController.deleteFromBasketById(1);
        List<Basket> basketItems2 = basketController.listProducts();
        assertEquals(basketItems2.size(), 1);

    }

    @Test
    public void testDeleteFromBasketByProductIdAndUserId() {
        basketController.addToBasket(new Basket(1, 1));
        basketController.addToBasket(new Basket(1, 2));
        basketController.addToBasket(new Basket(2, 1));

        List<Basket> basketItems1 = basketController.listProducts();
        assertEquals(basketItems1.size(), 3);

        basketController.deleteFromBasketByProductIdAndUserId(1, 1);
        List<Basket> basketItems2 = basketController.listProducts();
        assertEquals(basketItems2.size(), 2);

    }

}
