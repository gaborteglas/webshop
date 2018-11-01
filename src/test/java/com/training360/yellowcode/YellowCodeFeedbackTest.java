package com.training360.yellowcode;

import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.database.FeedbackDao;
import com.training360.yellowcode.dbTables.*;
import com.training360.yellowcode.userinterface.FeedbackController;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/clearForFeedback.sql")
public class YellowCodeFeedbackTest {

    @Autowired
    private UserController userController;

    @Autowired
    private ProductController productController;

    @Autowired
    private FeedbackController feedbackController;

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
    public void testCreateFeedback() {
        User user = new User(1, "feedbackUser", "Feedback User",
                "Feedback1", UserRole.ROLE_USER );

        Feedback testFeedback = new Feedback(4, "Naggyon király", LocalDateTime.now(), user);
        feedbackController.createFeedback(testFeedback, 1);

        List<Feedback> allFeedbacks = productController.findProductByAddress("aliceblue").get().getFeedbacks();

        assertEquals(1, allFeedbacks.size());
        assertEquals(4, allFeedbacks.get(0).getRatingScore());
        assertEquals("Naggyon király", allFeedbacks.get(0).getRatingText());
        assertEquals(user.getLoginName(), allFeedbacks.get(0).getUser().getLoginName());
    }

    @Test
    public void testCreateFeedbackAsUnregistered() {
        Feedback testFeedback = new Feedback(4, "Naggyon király", LocalDateTime.now(), null);
        Response response = feedbackController.createFeedback(testFeedback, 1);

        assertEquals("Értékelés írásához kérjük, jelentkezz be!", response.getMessage());
    }

    @Test
    @WithMockUser(username = "feedbackUser", roles = "USER")
    public void testCreateSecondFeedbackSameUser() {
        User user = new User(1, "feedbackUser", "Feedback User",
                "Feedback1", UserRole.ROLE_USER );

        Feedback testFeedback = new Feedback(4, "Naggyon király", LocalDateTime.now(), user);
        feedbackController.createFeedback(testFeedback, 1);

        Response response = feedbackController.createFeedback(testFeedback, 1);

        assertEquals("A megadott terméket már értékelte, amennyiben módosítani szeretné értékelését, a szerkesztés gombra kattintva megteheti.",
                response.getMessage());
    }

    @Test
    @WithMockUser(username = "feedbackUser", roles = "USER")
    public void testListFeedback() {
        User user2 = new User(2, "feedbackUser", "Feedback User",
                "Feedback2", UserRole.ROLE_USER );

        Feedback testFeedback2 = new Feedback(5, "Még nagyobb király", LocalDateTime.now(), user2);
        feedbackController.createFeedback(testFeedback2, 1);

        List<Feedback> allFeedbacks = productController.findProductByAddress("aliceblue").get().getFeedbacks();

        assertEquals(1, allFeedbacks.size());
        assertEquals(5, allFeedbacks.get(0).getRatingScore());
        assertEquals("Még nagyobb király", allFeedbacks.get(0).getRatingText());
        assertEquals(user2.getLoginName(), allFeedbacks.get(0).getUser().getLoginName());
    }
}
