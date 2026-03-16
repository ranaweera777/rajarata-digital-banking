package com.rajarata.bank.models.transactions;

import java.time.LocalDateTime;

public class TransferTransaction extends Transaction {
    public TransferTransaction(String id, LocalDateTime timestamp, double amount) {
        super(id, timestamp, "TRANSFER", amount);
    }
}
