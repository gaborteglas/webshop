package com.training360.yellowcode.dbTables;

import java.time.LocalDateTime;

public class Feedback {

    private int id;
    private int ratingScore;
    private String ratingText;
    private LocalDateTime ratingDate;
    private User user;

    public Feedback() {}

    public Feedback(int ratingScore, String ratingText, LocalDateTime ratingDate, User user) {
        this.ratingScore = ratingScore;
        this.ratingText = ratingText;
        this.ratingDate = ratingDate;
        this.user = user;
    }

    public Feedback(int id, int ratingScore, String ratingText, LocalDateTime ratingDate, User user) {
        this.id = id;
        this.ratingScore = ratingScore;
        this.ratingText = ratingText;
        this.ratingDate = ratingDate;
        this.user = user;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public LocalDateTime getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(LocalDateTime ratingDate) {
        this.ratingDate = ratingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
