package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.userinterface.ProductController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clear.sql")
public class YellowcodeApplicationTests {

    @Autowired
    private ProductController productController;

    @Before
    public void init() {
        productController.createProduct(new Product(1, "Az aliceblue 50 árnyalata", "aliceblue", "E. L. Doe", 9999, "active"));
        productController.createProduct(new Product(2, "Legendás programozók és megfigyelésük", "legendas","J. K. Doe",  3999, "active"));
        productController.createProduct(new Product(3, "Az 50 első Trainer osztály", "trainer", "Jack Doe", 5999, "active"));
        productController.createProduct(new Product(4, "Hogyan neveld a junior fejlesztődet", "junior", "Jane Doe", 6499, "active"));
        productController.createProduct(new Product(5, "Junior most és mindörökké", "mindorokke", "James Doe", 2999, "active"));
    }

    @Test
    public void testFindProductByAddress() {

        Optional<Product> product = productController.findProductByAddress("aliceblue");
        assertEquals(product.get().getName(), "Az aliceblue 50 árnyalata");
        assertEquals(product.get().getAddress(), "aliceblue");
        assertEquals(product.get().getProducer(), "E. L. Doe");
        assertEquals(product.get().getCurrentPrice(), 9999);
        assertEquals(product.get().getId(), 1);

    }

    @Test
    public void testFindProductByInvalidAddress() {
        Optional<Product> product = productController.findProductByAddress("henger");
        assertEquals(product, Optional.empty());
    }


    @Test
    public void testCreateProduct() {
        List<Product> products = productController.listProducts();

        productController.createProduct(new Product(
                6, "A Java ura: A classok szövetsége", "szovetseg", "J.R.R. Doe", 2899, "active"
                ));
        List<Product> products2 = productController.listProducts();
        assertEquals(products.size(), 5);
        assertEquals(products2.size(), 6);

    }

    @Test
    public void testUpdateProduct() {
        productController.createProduct(new Product(
                6, "A Java ura: A classok szövetsége", "szovetseg", "J.R.R. Doe", 2899, "active"
        ));
        productController.updateProduct(new Product(
                6, "A Java ura: A classok szövetsége", "szovetseg", "J.R.R. Doe", 3899, "active"),
                6);

        List<Product> products = productController.listProducts();
        assertEquals(products.size(), 6);
        assertEquals(productController.findProductByAddress("szovetseg").get().getCurrentPrice(), 3899);
    }

}
