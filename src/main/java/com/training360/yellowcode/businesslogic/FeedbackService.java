package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.FeedbackDao;
import com.training360.yellowcode.dbTables.Feedback;
import com.training360.yellowcode.dbTables.User;

public class FeedbackService {

    private FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public void createFeedback(Feedback feedback, long productId, User user) {
        feedback.setUser(user);
        feedbackDao.createFeedback(feedback, productId);
    }

}
