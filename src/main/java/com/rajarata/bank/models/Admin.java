package com.rajarata.bank.models;

public class Admin extends User {
    public Admin(String name, String email, String password, String createdAt, boolean isActive) {
        super(name, email, password, "ADMIN", password, createdAt, isActive);
    }
}
