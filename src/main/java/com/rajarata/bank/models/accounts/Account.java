package com.rajarata.bank.models.accounts;
// package com.rajarata.bank.models.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rajarata.bank.models.transactions.Transaction;

public abstract class Account {
    private String accountNumber;
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    protected Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    protected void setBalance(double balance) { this.balance = balance; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds");
        balance -= amount;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) transactions.add(transaction);
    }

    public List<Transaction> getTransactions() { return Collections.unmodifiableList(transactions); }
}
