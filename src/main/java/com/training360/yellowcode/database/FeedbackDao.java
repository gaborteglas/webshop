package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FeedbackDao {

    private JdbcTemplate jdbcTemplate;

    public FeedbackDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createFeedback(Feedback feedback, long productId) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into feedback(rating_text, rating_score, rating_date, product_id, user_id) " +
                                "values(?, ?, ?, ?, ?)"
                );
                ps.setString(1, feedback.getRatingText());
                ps.setInt(2, feedback.getRatingScore());
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                ps.setLong(4, productId);
                ps.setLong(5, feedback.getUser().getId());

                return ps;
            }
        });
    }

    public List<Feedback> findFeedBacksByProductId(long productId) {
        return jdbcTemplate.query("select feedback.rating_text, feedback.rating_score, feedback.rating_date, feedback.user_id, " +
                "users.user_name from feedback join users on feedback.user_id = users.id " +
                "where feedback.product_id = ?", new FeedbackMapper(), productId);
    }

    private static class FeedbackMapper implements RowMapper<Feedback> {
        @Override
        public Feedback mapRow(ResultSet resultSet, int i) throws SQLException {
            int ratingScore = resultSet.getInt("feedback.rating_score");
            String ratingText = resultSet.getString("feedback.rating_text");
            LocalDateTime localDateTime = resultSet.getTimestamp("feedback.rating_date").toLocalDateTime();
            long userId = resultSet.getLong("feedback.user_id");
            String username = resultSet.getString("users.user_name");
            return new Feedback(ratingScore, ratingText, localDateTime,
                    new User(userId, username));
        }
    }


}
