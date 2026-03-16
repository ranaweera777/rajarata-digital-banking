package com.rajarata.bank.models;

import java.time.LocalDateTime;

public abstract class User {
    // Encapsulation: private fields with getters/setters

    private String Id;
    private String name;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt;
    private boolean isActive;

    // Constructor
    protected User(String name, String email, String password, String role, String Id, LocalDateTime createdAt, boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.Id = Id;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public String getName() { return name; }
        public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

    public String getId() { return Id; }

        public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
    
    public String getRole() { return role; }
}
