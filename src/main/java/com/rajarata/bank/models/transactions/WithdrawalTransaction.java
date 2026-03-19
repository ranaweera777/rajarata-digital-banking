package com.rajarata.bank.models.transactions;

import java.time.LocalDateTime;

public class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction(String id, LocalDateTime timestamp, double amount) {
        super(id, timestamp, "WITHDRAWAL", amount);
    }
    
}
