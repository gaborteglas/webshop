package com.training360.yellowcode.database;

import com.training360.yellowcode.businesslogic.ProductService;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.User;
import com.training360.yellowcode.dbTables.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Repository
public class UserDao {


    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String loginName = resultSet.getString("user_name");
            String fullName = resultSet.getString("full_name");
            String password = resultSet.getString("password");
            UserRole role = UserRole.valueOf(resultSet.getString("role"));
            return new User(id, loginName, fullName, password, role);
        }
    }

    public void createUser(User user) {
        List<User> result = jdbcTemplate.query(
                "select id, user_name, full_name, password, role from users where id = ? OR user_name = ?",
                new UserMapper(), user.getId(), user.getLoginName());
        if (result.size() == 0) {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into users(id, user_name, full_name, password, enabled, role) values(?, ?, ?, ?, 1, ?)"
                );
                ps.setLong(1, user.getId());
                ps.setString(2, user.getLoginName());
                ps.setString(3, user.getFullName());
                ps.setString(4, user.getPassword());
                ps.setString(6, user.getRole());
                ProductService.LOGGER.info(MessageFormat.format("User added(id: {0}, login-name: {1}," +
                                " full-name: {2}, password: {3}, role: {4})", user.getId(), user.getLoginName(),
                        user.getFullName(), user.getPassword(), user.getRole()));
                return ps;
            });
        } else {
            throw new DuplicateProductException("A user with this id or login-name already exists.");
        }
    }

    private List<User> sortUsersByName(List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(User::getLoginName))
                .collect(Collectors.toList());
    }

    public List<User> listUsers() {
        return sortUsersByName(
                jdbcTemplate.query("select id, user_name, full_name, password, enabled, role from users",
                        new UserDao.UserMapper())
        );
    }

    public void updateUser(long id, String name, String password) {
        namePasswordEmpty(name);
        namePasswordEmpty(password);
        List<User> result = jdbcTemplate.query(
                "select id, user_name, full_name, password, enabled, role from users where id = ?",
                new UserDao.UserMapper(), id);
        if (result.size() == 1) {
            jdbcTemplate.update(
                    "update users set full_name = ?, password = ? where id = ?",
                    name, password, id);
        } else {
            throw new IllegalArgumentException("No user found");
        }
    }

    private void namePasswordEmpty(String string) {
        if(string == null || "".equals(string.trim())) {
            throw new IllegalArgumentException("Null not allowed here");
        }
    }

    public void deleteUser(long id) {
        jdbcTemplate.update("delete from users where id = ?", id);
        jdbcTemplate.update("update basket set user_id = 0 where user_id = ?", id);
    }
}
