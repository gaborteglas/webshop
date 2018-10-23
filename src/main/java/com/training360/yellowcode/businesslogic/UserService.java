package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.UserDao;
import com.training360.yellowcode.dbTables.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user) {
        userDao.createUser(user);
    }
}
