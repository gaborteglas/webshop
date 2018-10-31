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

    public FeedbackService(FeedbackDao feedbackDao, ProductDao productDao) {
        this.feedbackDao = feedbackDao;
        this.productDao = productDao;
    }

    public void createFeedback(Feedback feedback, long productId, User user) {
        feedback.setUser(user);
        Optional<Product> product = productDao.findProductById(productId);
        product.get().addToFeedbackList(feedback);

        feedbackDao.createFeedback(feedback, productId);
    }

}
