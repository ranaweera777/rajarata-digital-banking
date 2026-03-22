package com.rajarata.bank.models.transactions;
import  com.rajarata.bank.models.accounts.Account;

import java.time.LocalDateTime;

public abstract class DepositTransaction extends Transaction {
    private Account targetAccount;


    public DepositTransaction(String id, LocalDateTime timestamp, double amount, String description, String status, Account targetAccount) {
        super(id, timestamp, "DEPOSIT", amount, description, status);
        this.targetAccount = targetAccount;
    }

@Override
public String getTransactionType() {
    return "DEPOSIT";

}
@Override
public  boolean execute(){
    boolean success = targetAccount.deposit(getAmount());
    if (success){
        markSuccess();
        targetAccount.addTransaction(this);
    } else {
        markFailed();
    }
    return success;
}

}
