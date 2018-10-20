package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.Product;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UserFriendlyAddressGeneratorTest {

    @Test
    public void userFriendlyAddressGeneratorTest() {
        Product product = new Product(2, "80 nap alatt a Java körül", "javakorul",
                "Jules Doe", 3000, "active");
        assertEquals(product.userFriendlyAddressGenerator(), "80-nap-alatt-a-java-korul");
    }
}
