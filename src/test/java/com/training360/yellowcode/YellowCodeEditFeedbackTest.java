package com.training360.yellowcode;

import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.database.FeedbackDao;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/clearForFeedback.sql")
public class YellowCodeEditFeedbackTest {

    @Autowired
    private UserController userController;

    @Autowired
    private ProductController productController;

    @Autowired
    private FeedbackController feedbackController;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private OrdersController ordersController;

    @Autowired
    private BasketController basketController;

    @Before
    public void init(){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testadmin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        productController.createProduct(
                new Product(1, "Az aliceblue 50 árnyalata", "aliceblue", "E. L. Doe",
                        9999, ProductStatusType.ACTIVE, new Category(1, "Egyéb", 1L)));


        userController.createUser(new User(1, "feedbackUser", "Feedback User",
                "Feedback1", UserRole.ROLE_USER ));

        SecurityContextHolder.getContext().setAuthentication(a);
    }

    @Test
    @WithMockUser(username = "feedbackUser", roles = "USER")
    public void editFeedback() {
        User user = new User(1, "feedbackUser", "Feedback User",
                "Feedback1", UserRole.ROLE_USER );

        basketController.addToBasket(1, 1L);
        ordersController.createOrderAndOrderItems("szállítási cím");

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testadmin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        ordersController.modifyActiveStatusToDelivered(1);

        SecurityContextHolder.getContext().setAuthentication(a);

        Feedback testFeedback = new Feedback(4, "Naggyon király", LocalDateTime.now(), user);
        feedbackController.createFeedback(testFeedback, 1);
        Product product = productController.findProductByAddress("aliceblue"). get();
        List<Feedback> allFeedbacks = feedbackDao.findFeedBacksByProductId(1);
        assertEquals(1, allFeedbacks.size());
        assertEquals(4, allFeedbacks.get(0).getRatingScore());

        Feedback testFeedback2 = feedbackDao.findFeedBackByProductIdAndUserId(1, 1);
        testFeedback2.setRatingScore(1);
        testFeedback2.setRatingText("Meh");
        Response response = feedbackController.modifyFeedbackByUser(testFeedback2, 1,
                1);
        System.out.println(response.getMessage());
        assertTrue(response.isValidRequest());
        allFeedbacks = feedbackDao.findFeedBacksByProductId(1);
        assertEquals(1, allFeedbacks.get(0).getRatingScore());
    }

    @Test
    @WithMockUser(username = "feedbackUser", roles = "USER")
    public void deleteFeedback() {
        User user = new User(1, "feedbackUser", "Feedback User",
                "Feedback1", UserRole.ROLE_USER );

        basketController.addToBasket(1, 1L);
        ordersController.createOrderAndOrderItems("szállítási cím");

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testadmin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        ordersController.modifyActiveStatusToDelivered(1);

        SecurityContextHolder.getContext().setAuthentication(a);

        Feedback testFeedback = new Feedback(4, "Naggyon király", LocalDateTime.now(), user);
        feedbackController.createFeedback(testFeedback, 1);
        Product product = productController.findProductByAddress("aliceblue"). get();
        List<Feedback> allFeedbacks = product.getFeedbacks();
        assertEquals(1, allFeedbacks.size());
        assertEquals(4, allFeedbacks.get(0).getRatingScore());
        Response response = feedbackController.deleteFeedbackByUser(product.getId(), allFeedbacks.get(0).getId());
        assertTrue(response.isValidRequest());
        System.out.printf(response.getMessage());
        List<Feedback> allFeedbacks2 = feedbackDao.findFeedBacksByProductId(1);
        assertEquals(0, allFeedbacks2.size());
    }
}
