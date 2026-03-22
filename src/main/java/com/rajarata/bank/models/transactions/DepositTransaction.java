package com.rajarata.bank.models.transactions;

import java.time.LocalDateTime;

public abstract class DepositTransaction extends Transaction {
    public DepositTransaction(String id, LocalDateTime timestamp, double amount, String description, String status) {
        super(id, timestamp, "DEPOSIT", amount, description, status);
    }
}
