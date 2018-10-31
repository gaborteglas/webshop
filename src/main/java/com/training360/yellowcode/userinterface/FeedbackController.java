package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.FeedbackService;
import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.businesslogic.UserService;
import com.training360.yellowcode.dbTables.Feedback;
import com.training360.yellowcode.dbTables.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class FeedbackController {

    private FeedbackService feedbackService;
    private UserService userService;

    public FeedbackController(FeedbackService feedbackService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @RequestMapping(value = "/api/products/{productId}/feedback", method = RequestMethod.POST)
    public Response createFeedback(@RequestBody Feedback feedback, @PathVariable long productId) {
        User user = getAuthenticatedUserId();
        if (user != null) {
            feedbackService.createFeedback(feedback, productId, user);
            return new Response(true, "Értékelés hozzáadva.");
        } else {
            return new Response(false, "Értékelés írásához kérjük, jelentkezz be!");
        }
    }

    private User getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {     //nincs bejelentkezve
            return null;
        }
        User user = userService.findUserByUserName(authentication.getName()).get();
        return user;
    }

}
