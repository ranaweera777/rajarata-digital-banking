package com.rajarata.bank.exceptions;

public class AuthenticationException extends RuntimeException {
    private String username;
    private int failedAttempts;





    public AuthenticationException(String message , String username, int failedAttempts) {
        super(message);
        this.username = username;
        this.failedAttempts = failedAttempts;
    }

    public String getUsername() {
        return username;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }
}
