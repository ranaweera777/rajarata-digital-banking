// package com.rajarata.bank.models.accounts;

// public class SavingsAccount extends Account {
//     public SavingsAccount(String accountNumber, double initialBalance) {
//         super(accountNumber, initialBalance);
//     }
// }
package com.rajarata.bank.models.accounts;

// Inheritance & Polymorphism
public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 4.5; // 4.5% annual interest
    private static final double MINIMUM_BALANCE = 500.0;
    private static final double WITHDRAWAL_LIMIT = 50000.0;

    public SavingsAccount(String accountId, String accountNumber, double initialDeposit, String currency) {
        super(accountId, accountNumber, initialDeposit, currency);
        this.minimumBalance = MINIMUM_BALANCE;
        this.withdrawalLimit = WITHDRAWAL_LIMIT;
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }

    @Override
    public double calculateInterest() {
        // Monthly interest calculation
        return (getBalance() * INTEREST_RATE) / 100 / 12;
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }
}