package com.rajarata.bank.models.transactions;

import com.rajarata.bank.models.accounts.Account;
import java.time.LocalDateTime;

public abstract class TransferTransaction extends Transaction {
  private Account sourceAccount;
  private Account targetAccount;

  public TransferTransaction(String id, LocalDateTime timestamp, double amount, String description, String status,
      Account sourceAccount, Account targetAccount) {
    super(id, timestamp, "TRANSFER", amount, description, status);
    this.sourceAccount = sourceAccount;
    this.targetAccount = targetAccount;
  }

  @Override
  public String getTransactionType() {
    return "TRANSFER";
  }

  @Override
  public boolean execute() {
    try {
      sourceAccount.withdraw(getAmount());
      targetAccount.deposit(getAmount());
      markSuccess();
      sourceAccount.addTransaction(this);
      targetAccount.addTransaction(this);
      return true;
    } catch (IllegalArgumentException exception) {
      markFailed();
      return false;
    }
  }

  public Account getSourceAccount() {
    return sourceAccount;
  }

  public Account getTargetAccount() {
    return targetAccount;
  }

}

