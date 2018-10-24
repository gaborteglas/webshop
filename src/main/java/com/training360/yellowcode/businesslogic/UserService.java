package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.UserDao;
import com.training360.yellowcode.dbTables.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user) {
        userDao.createUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void updateUser(long id, String name, String password) {
        userDao.updateUser(id, name, password);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    public User findUserByUserName(String userName) {
        return userDao.findUserByUserName(userName);
    }
}
