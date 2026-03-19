package com.rajarata.bank.models;

import java.time.LocalDateTime;

public abstract class User { 
    // Encapsulation: private fields with getters/setters

    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt;
    private boolean isActive;

    // Constructor
    protected User(String name, String email, String password, String role, String id, LocalDateTime createdAt, boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id = id;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }

    public String getPassword() { return password; }
        public void setPassword(String password) { 
     if (password != null && !password.isBlank()) this.password = password; }

    public String getId() { return id; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
    
    public String getRole() { return role; }
}
