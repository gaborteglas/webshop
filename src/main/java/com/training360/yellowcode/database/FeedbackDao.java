package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class FeedbackDao {

    private JdbcTemplate jdbcTemplate;

    public FeedbackDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean didUserReviewProduct(long productId, long userId) {
        List<Long> result = jdbcTemplate.query("select count(*) as reviewCount from feedback " +
                            "where product_id = ? and user_id = ?",
                (ResultSet resultSet, int i) -> resultSet.getLong("reviewCount"), productId, userId);

        return result.get(0) != 0;
    }

    public void createFeedback(Feedback feedback, long productId) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into feedback(id, rating_text, rating_score, rating_date, product_id, user_id) " +
                                "values(?, ?, ?, ?, ?, ?)"
                );
                ps.setLong(1, feedback.getId());
                ps.setString(2, feedback.getRatingText());
                ps.setInt(3, feedback.getRatingScore());
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                ps.setLong(5, productId);
                ps.setLong(6, feedback.getUser().getId());

                return ps;
            }
        });
    }

    public List<Feedback> findFeedBacksByProductId(long productId) {
        return jdbcTemplate.query("select feedback.id, feedback.rating_text, feedback.rating_score, feedback.rating_date, feedback.user_id, " +
                "users.user_name from feedback join users on feedback.user_id = users.id " +
                "where feedback.product_id = ? order by feedback.rating_date desc",
                new FeedbackMapper(), productId);
    }

    public Feedback findFeedBackByProductIdAndUserId(long productId, long userId) {
        return jdbcTemplate.queryForObject("select feedback.id, feedback.rating_text, feedback.rating_score, feedback.rating_date, feedback.user_id, " +
                        "users.user_name from feedback join users on feedback.user_id = users.id " +
                        "where feedback.product_id = ? AND feedback.user_id = ? order by feedback.rating_date desc",
                new FeedbackMapper(), productId, userId);
    }

    public long findProductIdByFeedbackId(long feedbackId) {
        Feedback feedback = jdbcTemplate.queryForObject("select feedback.id, feedback.rating_text, feedback.rating_score, " +
                        "feedback.rating_date, feedback.user_id, users.user_name from feedback join users on feedback.user_id = users.id " +
                        "where feedback.id = ?",
                new FeedbackMapper(), feedbackId);
        return feedback.getId();
    }

    private static class FeedbackMapper implements RowMapper<Feedback> {
        @Override
        public Feedback mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("feedback.id");
            int ratingScore = resultSet.getInt("feedback.rating_score");
            String ratingText = resultSet.getString("feedback.rating_text");
            LocalDateTime localDateTime = resultSet.getTimestamp("feedback.rating_date").toLocalDateTime();
            long userId = resultSet.getLong("feedback.user_id");
            String username = resultSet.getString("users.user_name");
            return new Feedback(id, ratingScore, ratingText, localDateTime,
                    new User(userId, username));
        }
    }

    public void deleteFeedbackByUser(long productId, long userId) {
        jdbcTemplate.update("delete from feedback where product_id = ? and user_id = ?",
                productId, userId);
    }

    public void modifyFeedbackByUser(Feedback feedback, long productId) {
        jdbcTemplate.update("update feedback set rating_text = ?, rating_score = ? where product_id = ? and user.id = ?",
                productId, feedback.getUser().getId());
    }


}
