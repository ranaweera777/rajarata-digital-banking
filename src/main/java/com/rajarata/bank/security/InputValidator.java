package com.rajarata.bank.security;
import java.util.regex.Pattern;

public class InputValidator {
        private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\+?[0-9]{10,15}$");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = 
        Pattern.compile("^[0-9]{10,16}$");



    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

        public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

      public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

       public static boolean isValidAmount(double amount) {
        return amount > 0 && amount < 1_000_000_000; // Max 1 billion
    }

     public static String sanitizeInput(String input) {
        if (input == null) return null;
        // Remove potentially dangerous characters
        return input.replaceAll("[<>\"'&]", "").trim();
    }



}
