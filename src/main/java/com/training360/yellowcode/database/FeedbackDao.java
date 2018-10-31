package com.training360.yellowcode.database;

import com.training360.yellowcode.dbTables.Feedback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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


}
