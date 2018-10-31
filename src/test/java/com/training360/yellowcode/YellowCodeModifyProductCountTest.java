package com.training360.yellowcode;


import com.training360.yellowcode.dbTables.*;
import com.training360.yellowcode.userinterface.BasketController;
import com.training360.yellowcode.userinterface.OrdersController;
import com.training360.yellowcode.userinterface.ProductController;
import com.training360.yellowcode.userinterface.UserController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"classpath:/clear.sql", "classpath:/clearorders.sql", "classpath:/clearbaskets.sql", "classpath:/clearusers.sql"})
public class YellowCodeModifyProductCountTest {

    @Autowired
    private BasketController basketController;

    @Autowired
    private OrdersController ordersController;

    @Autowired
    private ProductController productController;

    @Autowired
    private UserController userController;

    @Before
    public void addProductsAndUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testadmin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        productController.createProduct(new Product(1, "Az aliceblue 50 árnyalata", "aliceblue",
                "E. L. Doe", 9999, ProductStatusType.ACTIVE, new Category(1, "Egyéb", 1L)));
        productController.createProduct(new Product(2, "Legendás programozók és megfigyelésük",
                "legendas", "J. K. Doe", 3999, ProductStatusType.ACTIVE, new Category(1, "Egyéb", 1L)));

        userController.createUser(new User(1, "user1", "Test One", "Elsőjelszó1", UserRole.ROLE_USER));

        SecurityContextHolder.getContext().setAuthentication(a);
    }

}
