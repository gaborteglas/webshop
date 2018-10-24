package com.training360.yellowcode;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.ProductStatusType;
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
import java.util.Optional;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clear.sql")
@WithMockUser(username = "testadmin", roles = "ADMIN")
public class YellowcodeApplicationTests {

    @Autowired
    private ProductController productController;

    @Before
    public void init() {
        productController.createProduct(new Product(1, "Az aliceblue 50 árnyalata","aliceblue", "E. L. Doe", 9999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(2, "Legendás programozók és megfigyelésük", "legendas", "J. K. Doe",  3999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(3, "Az 50 első Trainer osztály", "osztaly", "Jack Doe", 5999, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(4, "Hogyan neveld a junior fejlesztődet", "junior", "Jane Doe", 6499, ProductStatusType.ACTIVE));
        productController.createProduct(new Product(5, "Junior most és mindörökké", "mindorokke", "James Doe", 2999, ProductStatusType.ACTIVE));
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
                6, "A Java ura: A classok szövetsége", "a-java-ura:-a-classok-szovetsege", "J.R.R. Doe", 2899, ProductStatusType.ACTIVE
                ));
        List<Product> products2 = productController.listProducts();
        assertEquals(products.size(), 5);
        assertEquals(products2.size(), 6);

    }

    @Test
    public void testUpdateProduct() {
        productController.createProduct(new Product(
                6, "A Java ura: A classok szövetsége", "a-java-ura:-a-classok-szovetsege", "J.R.R. Doe", 2899, ProductStatusType.ACTIVE
        ));
        productController.updateProduct(new Product(
                6, "A Java ura: A classok szövetsége", "a-java-ura:-a-classok-szovetsege", "J.R.R. Doe", 3899, ProductStatusType.ACTIVE),
                6);

        List<Product> products = productController.listProducts();
        assertEquals(products.size(), 6);
        assertEquals(productController.findProductByAddress("a-java-ura:-a-classok-szovetsege").get().getCurrentPrice(), 3899);
    }

    @Test
    public void testDeleteProduct() {
        productController.createProduct(new Product(
                6, "A Java ura: A classok szövetsége", "a-java-ura:-a-classok-szovetsege", "J.R.R. Doe", 2899, ProductStatusType.ACTIVE
        ));
        productController.deleteProduct(6);

        List<Product> products = productController.listProducts();
        List<Product> allProducts = productController.listAllProducts();

        assertEquals(products.size(), 5);
        assertEquals(allProducts.size(), 6);

        assertEquals(productController.findProductByAddress("a-java-ura:-a-classok-szovetsege").get().getStatus(), ProductStatusType.INACTIVE);
    }

    //Ez átmeneti, amíg a CRUD nem teljes
    @Test
    public void testListSortedProductsByNameThenProducer() {
        List<Product> products = productController.listProducts();
        assertEquals(products.size(), 5);
        assertEquals(products.get(0).getName(), "Az 50 első Trainer osztály");
        assertEquals(products.get(products.size() - 1).getName(), "Legendás programozók és megfigyelésük");
    }



}
