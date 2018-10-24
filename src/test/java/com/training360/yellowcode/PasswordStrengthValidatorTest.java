package com.training360.yellowcode;

import com.training360.yellowcode.userinterface.UserController;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PasswordStrengthValidatorTest {
    @Autowired
    private UserController userController;

    @Test
    public void passwordStrengthValidatorTestValid() {
        assertTrue(userController.passwordStrengthValidator("JohnDoe550$"));
    }

    @Test
    public void passwordStrengthValidatorTestSufferingLevel() {
        assertTrue(userController.passwordStrengthValidator("@UO80pJmWsN&U#O^!UCB^i!IOTNO57"));
    }

    @Test
    public void passwordStrengthValidatorTestWithoutNumber() {
        assertFalse(userController.passwordStrengthValidator("JohnDoee"));
    }

    @Test
    public void passwordStrengthValidatorTestWithoutCapitalLetter() {
        assertFalse(userController.passwordStrengthValidator("johndoe220"));
    }

    @Test
    public void passwordStrengthValidatorTestTooShort() {
        assertFalse(userController.passwordStrengthValidator("Valami1"));
    }
}
