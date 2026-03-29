package com.rajarata.bank.services;

import com.rajarata.bank.models.User;
import com.rajarata.bank.exceptions.AuthenticationException;
import com.rajarata.bank.security.PasswordValidator;
import com.rajarata.bank.security.InputValidator;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private User currentUser;
    private Map<String, User> users;
    private Map<String, Integer>failedAttempts;
    private static final int MAX_FAILED_ATTEMPTS = 3;
    // private User currentUser;
    // private User username;


    private final Map<String, User> usersByEmail = new HashMap<>();

    public AuthenticationService(){
        this.users = new HashMap<>();
        this.failedAttempts = new HashMap<>();
    }

    public boolean register(User user , String password) {
        //modify the input validator
        if (!InputValidator.isValidEmail(user.getEmail())) {
            System.out.println("Invalid email format: " );
            // usersByEmail.put(user.getEmail().toLowerCase(), user);
            return false;
        }
        //modify password validator
        if (!PasswordValidator.isStrongPassword(password)) {
            System.out.println("Password does not meet security requirements");
            return false;
        }
          if (users.containsKey(user.getName())) {
            System.out.println("Username already exists");
            return false;
        }
          users.put(user.getName(), user);
        System.out.println("User registered successfully: " + user.getName());
        return true;


    }

 public User login(String username, String password)throws AuthenticationException{
            int attempts = failedAttempts.getOrDefault(password, 0);
            if(attempts >= MAX_FAILED_ATTEMPTS){
                throw new AuthenticationException("Account locked due to too many failed login attempts: " + username, username, attempts);
            }

            User user = users.get(username);
            if(user == null){
                throw new AuthenticationException("User not found: " + username, username, 0);
            }
                   //modify password validator
                  if (!PasswordValidator.isStrongPassword(password))  {
            failedAttempts.put(username, attempts + 1);
            throw new AuthenticationException("Invalid password", username, attempts + 1);
        }

               if (!user.isActive()) {
            throw new AuthenticationException("Account is deactivated", username, 0);
        }

        failedAttempts.remove(username);
        currentUser = user;
        System.out.println("Login successful: " + username);
        return user;

    }

    public void logout(){
        if(currentUser != null){
            System.out.println("Logout" + currentUser.getName());
            currentUser = null;
        }
    }

     public User getCurrentUser(){
        return currentUser;
     }

       public void resetFailedAttempts(String username) {
        failedAttempts.remove(username);
    }
           

 }






