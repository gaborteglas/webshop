package com.training360.yellowcode.businesslogic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    public boolean passwordStrengthValidator(String password) {
        Pattern normalCase = Pattern.compile("[a-z]+");
        Pattern capitalCase = Pattern.compile("[A-Z]+");
        Pattern number = Pattern.compile("[0-9]+");
        Matcher normalCaseMatcher = normalCase.matcher(password);
        Matcher capitalCaseMatcher = capitalCase.matcher(password);
        Matcher numberMatcher = number.matcher(password);
        return password.matches(".{8,}") && normalCaseMatcher.find()
                && capitalCaseMatcher.find() && numberMatcher.find();
    }
}
