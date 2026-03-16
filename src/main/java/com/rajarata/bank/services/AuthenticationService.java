package com.rajarata.bank.services;

public class AuthenticationService {
    public boolean authenticate(String email, String password) {
        return email != null && password != null;
    }
}
