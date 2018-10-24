package com.training360.yellowcode.database;

import com.training360.yellowcode.businesslogic.PasswordValidator;
import com.training360.yellowcode.businesslogic.UserService;
import com.training360.yellowcode.dbTables.User;
import com.training360.yellowcode.dbTables.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
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

    public long createUser(User user) {
        List<User> result = jdbcTemplate.query(
                "select id, user_name, full_name, password, role from users where user_name = ?",
                new UserMapper(), user.getLoginName());
        if (result.size() == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into users(user_name, full_name, password, enabled, role) values(?, ?, ?, 1, ?)",
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getLoginName());
                    ps.setString(2, user.getFullName());
                    if (new PasswordValidator().passwordStrengthValidator(user.getPassword())) {
                        ps.setString(3, new BCryptPasswordEncoder().encode(user.getPassword()));
                    } else {
                        throw new IllegalArgumentException("Password is not valid");
                    }
                    ps.setString(4, user.getRole());
                    return ps;
                }
            }, keyHolder);
            long generatedId = keyHolder.getKey().longValue();
            UserService.LOGGER.info(MessageFormat.format(
                    "User added(id: {0}, login-name: {1}, full-name: {2}, password: {3}, role: {4})",
                    generatedId,
                    user.getLoginName(), user.getFullName(), user.getPassword(), user.getRole()));
            return generatedId;

        } else {
            throw new DuplicateUserException("A user with this id or login-name already exists.");
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
            String hashedPassword = new BCryptPasswordEncoder().encode(password);
            jdbcTemplate.update(
                    "update users set full_name = ?, password = ? where id = ?",
                    name, hashedPassword, id);
        }
        UserService.LOGGER.info("User modified to -> id: {0}, user_name: {1}, full_name: {2}, password: {3}",
                id, name, password);
    }

    private void namePasswordEmpty(String string) {
        if (string == null || "".equals(string.trim())) {
            throw new IllegalArgumentException("Null not allowed here");
        }
    }

    public void deleteUser(long id) {
        jdbcTemplate.update("delete from users where id = ?", id);
        jdbcTemplate.update("update basket set user_id = 0 where user_id = ?", id);
        UserService.LOGGER.info("User removed with id - {0}", id);
    }

    public User findUserByUserName(String userName) {
        List<User> users = jdbcTemplate.query("select id, user_name, full_name, password, enabled, role from users where user_name = ?",
                new UserMapper(),
                userName);
        return users.get(0);
    }

}
