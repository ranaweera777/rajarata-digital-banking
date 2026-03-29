package com.rajarata.bank.models.transactions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaction {
    private String id; //transactionId 
    private LocalDateTime timestamp;
    private String type;
    private double amount;
    private String description;
    private String status;

    public Transaction(String id, LocalDateTime timestamp, String type, double amount, String description, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.status = status; // status can be "PENDING", "SUCCESS", "FAILED"

    }

    // Abstract method - different transaction types implement differently
    public abstract String getTransactionType();
    public abstract boolean execute();
    

    public void markSuccess() { this.status = "SUCCESS"; }
    public void markFailed() { this.status = "FAILED"; }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s | %s | Amount: %.2f | Status: %s | %s",
                id, timestamp.format(formatter), getTransactionType(), amount, status, description);
    }

    public String getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
}
