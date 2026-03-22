package com.rajarata.bank.models.transactions;

import java.time.LocalDateTime;

public abstract class TransferTransaction extends Transaction {
    public TransferTransaction(String id, LocalDateTime timestamp, double amount, String description, String status) {
        super(id, timestamp, "TRANSFER", amount, description, status);
    }
}
