package com.rajarata.bank.security;

public class PasswordValidator {
    public boolean isValid(String password) {
        return password != null && password.length() >= 8;
    }
}
