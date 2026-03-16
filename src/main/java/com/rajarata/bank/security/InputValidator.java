package com.rajarata.bank.security;

public class InputValidator {
    public boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
