package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.FeedbackDao;
import com.training360.yellowcode.database.ProductDao;
import com.training360.yellowcode.dbTables.Feedback;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.User;

import java.util.List;
import java.util.Optional;

public class FeedbackService {

    private FeedbackDao feedbackDao;
    private ProductDao productDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public void createFeedback(Feedback feedback, long productId, User user) {
        feedback.setUser(user);
        feedbackDao.createFeedback(feedback, productId);
        Optional<Product> product = productDao.findProductById(productId);
        List<Feedback> feedbacks = product.get().getFeedbacks();
        feedbacks.add(feedback);
    }

}
