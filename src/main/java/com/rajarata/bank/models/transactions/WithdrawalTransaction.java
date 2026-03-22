package com.rajarata.bank.models.transactions;

import java.time.LocalDateTime;

public abstract class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction(String id, LocalDateTime timestamp, double amount, String description, String status) {
        super(id, timestamp, "WITHDRAWAL", amount, description, status);
    }
    
}
