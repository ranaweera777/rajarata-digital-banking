package com.rajarata.bank.models.transactions;
import  com.rajarata.bank.models.accounts.Account;

import java.time.LocalDateTime;

public abstract class WithdrawalTransaction extends Transaction {
    private Account sourceAccount;



    public WithdrawalTransaction(String id, LocalDateTime timestamp, double amount, String description, String status, Account sourceAccount) {
        super(id, timestamp, "WITHDRAWAL", amount, description, status);
        this.sourceAccount = sourceAccount;
    }

    @Override
    public String getTransactionType(){
        return "WITHDRAWAL";
    }

    // @Override
    // public boolean execute(){
    //     boolean success = sourceAccount.withdraw(getAmount());
    //     if (success){
    //         markSuccess();
    //         sourceAccount.addTransaction(this);
    //     } else {
    //         markFailed();
    //     }
    //     return success;
    // }
@Override
public boolean execute() {
    try {
        sourceAccount.withdraw(getAmount());
        markSuccess();
        sourceAccount.addTransaction(this);
        return true;
    } catch (IllegalArgumentException e) {
        markFailed();
        return false;
    }

}
}