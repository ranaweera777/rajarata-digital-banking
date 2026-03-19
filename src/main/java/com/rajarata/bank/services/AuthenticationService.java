package com.rajarata.bank.services;

import com.rajarata.bank.models.User;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private final Map<String, User> usersByEmail = new HashMap<>();

    public void registerUser(User user) {
        if (user != null && user.getEmail() != null) {
            usersByEmail.put(user.getEmail().toLowerCase(), user);
        }
    }

    public boolean authenticate(String email, String password) {
        if (email == null || password == null) return false;
        User user = usersByEmail.get(email.toLowerCase());
        return user != null && user.isActive() && user.getPassword().equals(password);
    }

    public User findByEmail(String email) {
        if (email == null) return null;
        return usersByEmail.get(email.toLowerCase());
    }
}
