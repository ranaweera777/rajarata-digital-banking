package com.rajarata.bank.models ;

public class Staff extends User {
    public Staff(String name, String email, String password) {
        super(name, email, password, "STAFF", password, createdAt, isActive);
    }
}
