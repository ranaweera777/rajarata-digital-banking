package com.rajarata.bank.models.accounts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rajarata.bank.interfaces.InterestCalculable;
import com.rajarata.bank.interfaces.Transactable;
import com.rajarata.bank.models.transactions.Transaction;

public abstract class Account implements Transactable, InterestCalculable {
    private String accountId;
    private String accountNumber;
    private double balance;
    private String currency;
    private LocalDateTime openedDate;
    private boolean active;
    private final List<Transaction> transactionHistory;

    protected double minimumBalance;
    protected double withdrawalLimit;
    protected double interestRate;

    protected Account(String accountNumber, double initialBalance) {
        this(null, accountNumber, initialBalance, "LKR");
    }

    protected Account(String accountId, String accountNumber, double initialDeposit, String currency) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
        this.currency = currency;
        this.openedDate = LocalDateTime.now();
        this.active = true;
        this.transactionHistory = new ArrayList<>();
        this.minimumBalance = 0;
        this.withdrawalLimit = Double.MAX_VALUE;
        this.interestRate = 0;
    }

    public String getAccountType() {
        return getClass().getSimpleName();
    }

    @Override
    public void deposit(double amount) {
        validatePositiveAmount(amount);
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        validatePositiveAmount(amount);

        if (amount > withdrawalLimit) {
            throw new IllegalArgumentException("Withdrawal amount exceeds the limit: " + withdrawalLimit);
        }

        if (balance - amount < minimumBalance) {
            throw new IllegalArgumentException("Insufficient funds. Minimum balance required: " + minimumBalance);
        }

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        balance -= amount;
    }

    @Override
    public boolean transfer(Transactable targetAccount, double amount) {
        if (targetAccount == null || targetAccount == this) {
            return false;
        }

        try {
            withdraw(amount);
            targetAccount.deposit(amount);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double calculateInterest() {
        return balance * (interestRate / 100.0);
    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        this.interestRate = interestRate;
    }

    @Override
    public void applyInterest() {
        balance += calculateInterest();
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactionHistory.add(transaction);
        }
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public List<Transaction> getTransactions() {
        return getTransactionHistory();
    }

    public String generateMonthlyStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("---------MONTHLY STATEMENT---------\n");
        statement.append("Account: ").append(accountNumber).append("\n");
        statement.append("Type: ").append(getAccountType()).append("\n");
        statement.append("Balance: ").append(balance).append(" ").append(currency).append("\n");
        statement.append("Interest Rate: ").append(getInterestRate()).append("%\n");
        statement.append("Transactions:\n");

        for (Transaction transaction : transactionHistory) {
            statement.append(" - ").append(transaction).append("\n");
        }

        statement.append("----------------------------------\n");
        return statement.toString();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    private void validatePositiveAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
