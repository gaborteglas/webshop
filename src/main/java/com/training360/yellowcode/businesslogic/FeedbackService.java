package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.FeedbackDao;

public class FeedbackService {

    private FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }
}
