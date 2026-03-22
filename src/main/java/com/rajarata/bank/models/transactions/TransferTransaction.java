package com.rajarata.bank.models.transactions;
import com.rajarata.bank.models.accounts.Account;
import java.time.LocalDateTime;

public abstract class TransferTransaction extends Transaction {
    private Account sourceAccount;
    private Account targetAccount;


    public TransferTransaction(String id, LocalDateTime timestamp, double amount, String description, String status) {
        super(id, timestamp, "TRANSFER", amount, description, status);
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

  @Override
  public String getTransactionType(){
    return "TRANSFER";
  }

  @Override
  public boolean execute(){
    boolean success = sourceAccount.withdraw(getAmount());
  }
    
  
  
  
  
    public Account getSourceAccount() { return sourceAccount; }
    public Account getTargetAccount() { return targetAccount; }


}
