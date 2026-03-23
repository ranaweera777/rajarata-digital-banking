package com.rajarata.bank.exceptions;


public class InvalidAccountException extends RuntimeException {
    private String accountNumber;


    public InvalidAccountException(String message,String accountNumber) {
        super(message);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    }

