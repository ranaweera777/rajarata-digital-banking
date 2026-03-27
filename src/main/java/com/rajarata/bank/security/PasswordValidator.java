package com.rajarata.bank.security;
import java.util.regex.Pattern;
// import java.util.regex.pattern;

public class PasswordValidator {

    private static final int MIN_LENGTH = 8;
    private static final Pattern UPPER_CASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]");

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < MIN_LENGTH) {
            return false;
        }
        boolean hasUpper = UPPER_CASE.matcher(password).find();
        boolean hasLower = LOWERCASE.matcher(password).find();
        boolean hasDigit = DIGIT.matcher(password).find();
        boolean hasSpecial = SPECIAL.matcher(password).find();

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public static String getPasswordRequirements() {
        return "Password must:\n" + "- Be at least " + MIN_LENGTH + " characters long\n" +
                "- Contain at least one uppercase letter\n" +
                "- Contain at least one lowercase letter\n" +
                "- Contain at least one digit\n" +
                "- Contain at least one special character";
        // return password != null && password.length() >= 8;
    }

    public static String hashPassword(String password){
        return Integer.toHexString(password.hashCode());
    }
}
