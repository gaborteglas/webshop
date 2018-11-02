package com.training360.yellowcode;

import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.dbTables.*;
import com.training360.yellowcode.userinterface.*;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/clearForFeedback.sql")
public class YellowCodeFeedbackByBuyerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private ProductController productController;

    @Autowired
    private FeedbackController feedbackController;

    @Autowired
    private OrdersController ordersController;

    @Autowired
    private BasketController basketController;

    @Before
    public void init(){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testadmin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        productController.createProduct(
                new Product(85, "Az aliceblack 50 árnyalata", "aliceblack", "E. L. Doe",
                        9999, ProductStatusType.ACTIVE, new Category(1, "Egyéb", 1L)));

        userController.createUser(new User(1, "feedbackUser", "Feedback User",
                "Feedback1", UserRole.ROLE_USER ));

        SecurityContextHolder.getContext().setAuthentication(a);

    }

    @Test
    @WithMockUser(username = "feedbackUser", roles = "USER")
    public void testFeedbackByBuyer() {
        User testUser = new User(1, "feedbackUser", "Feedback User",
                "Feedback1", UserRole.ROLE_USER );

        basketController.addToBasket(85, 1L);
        ordersController.createOrderAndOrderItems("aliceblue");

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testadmin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        ordersController.modifyActiveStatusToDelivered(1);

        SecurityContextHolder.getContext().setAuthentication(a);

        Response response = feedbackController.createFeedback(new Feedback(4, "Naggyon király", LocalDateTime.now(), testUser), 85 );

        assertEquals("Értékelés hozzáadva.", response.getMessage());
        assertTrue(response.isValidRequest());

        List<Feedback> feedbacks = productController.findProductByAddress("aliceblack").get().getFeedbacks();

        assertEquals(1, feedbacks.size());
        assertEquals("Naggyon király", feedbacks.get(0).getRatingText());
        assertEquals(4, feedbacks.get(0).getRatingScore());
    }

    @Test
    @WithMockUser(username = "feedbackUser", roles = "USER")
    public void testFeedbackByNonBuyer() {
        User testUser = new User(1, "feedbackUser", "Feedback User",
                "Feedback1", UserRole.ROLE_USER );

        Response response = feedbackController.createFeedback(new Feedback(4, "Naggyon király", LocalDateTime.now(), testUser), 85 );

        assertEquals("Kizárólag olyan termékeket tud értékelni, amelyeket már kiszállítottunk Önnek.", response.getMessage());
        assertFalse(response.isValidRequest());

        List<Feedback> feedbacks = productController.findProductByAddress("aliceblack").get().getFeedbacks();

        assertEquals(0, feedbacks.size());
    }
}
