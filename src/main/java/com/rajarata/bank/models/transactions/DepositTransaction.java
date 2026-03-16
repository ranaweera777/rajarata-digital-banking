package com.rajarata.bank.models.transactions;

import java.time.LocalDateTime;

public class DepositTransaction extends Transaction {
    public DepositTransaction(String id, LocalDateTime timestamp, double amount) {
        super(id, timestamp, "DEPOSIT", amount);
    }
}
