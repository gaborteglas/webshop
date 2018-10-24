package com.training360.yellowcode;

import com.training360.yellowcode.dbTables.User;
import com.training360.yellowcode.dbTables.UserRole;
import com.training360.yellowcode.userinterface.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearusers.sql")
@WithMockUser(username = "testadmin", roles = "ADMIN")
public class YellowcodeApplicationUserTests {

    @Autowired
    private UserController userController;

    @Before
    public void init() {
        userController.createUser(new User(1, "login1", "Test One", "abc", UserRole.ROLE_CUSTOMER));
        userController.createUser(new User(2, "login2", "Test Two", "def", UserRole.ROLE_CUSTOMER));
        userController.createUser(new User(3, "login3", "Test Three", "ghi",UserRole.ROLE_CUSTOMER));
    }

    @Test
    public void testListUsers() {
        List<User> users1 = userController.listUsers();

        assertEquals(users1.size(), 3);
    }

    @Test
    public void testCreateUsers() {
        List<User> users1 = userController.listUsers();

        userController.createUser(new User(4, "login4", "Test Four", "jkl", UserRole.ROLE_CUSTOMER));

        List<User> users2 = userController.listUsers();

        assertEquals(users1.size(), 3);
        assertEquals(users2.size(), 4);
    }

    @Test
    public void testDeleteUser() {
        userController.createUser(new User(4, "login4", "Test Four", "jkl", UserRole.ROLE_CUSTOMER));
        List<User> users1 = userController.listUsers();

        assertEquals(users1.size(), 4);

        userController.deleteUser(4);
        List<User> users2 = userController.listUsers();

        assertEquals(users2.size(), 3);
    }

    @Test
    public void testUpdateUser() {
        userController.createUser(new User(4, "login4", "Test Four", "jkl", UserRole.ROLE_CUSTOMER));

        userController.updateUser(new User(4, "login4", "changed", "other", UserRole.ROLE_CUSTOMER), 4);
        List<User> users1 = userController.listUsers();

        assertEquals(users1.get(3).getFullName(), "changed");
    }



}
