package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.userinterface.ProductController;
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

    @Test
    public void testFindProductByAddress() {

        Optional<Product> product = productController.findProductByAddress("hengermalom");
        assertEquals(product.get().getName(), "A hengermalomi bárdok");
        assertEquals(product.get().getAddress(), "hengermalom");
        assertEquals(product.get().getProducer(), "János Doe");
        assertEquals(product.get().getCurrentPrice(), 8399);
        assertEquals(product.get().getId(), 12);

    }

    //Ez átmeneti, amíg a CRUD nem teljes
    @Test
    public void testListSortedProductsByNameThenProducer() {
        List<Product> products = productController.listProducts();
        assertEquals(products.size(), 15);
        assertEquals(products.get(0).getName(), "80 nap alatt a Java körül");
        assertEquals(products.get(products.size() - 1).getName(), "Nemzeti Java");
    }

}
