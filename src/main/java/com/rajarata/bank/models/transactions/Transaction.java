package com.rajarata.bank.models.transactions;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private LocalDateTime timestamp;
    private String type;
    private double amount;

    public Transaction(String id, LocalDateTime timestamp, String type, double amount) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
    }

    public String getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
}
