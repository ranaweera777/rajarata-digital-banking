package com.rajarata.bank.models.accounts;

public class CheckingAccount extends Account {
    private static final double INTEREST_RATE = 3.0;
    private static final double MINIMUM_BALANCE = 0.0;
    private double overdraftLimit;
    private boolean overdraftProtection;

    public CheckingAccount(String accountId, String accountNumber, 
                           double initialDeposit, String currency, double overdraftLimit) {
        super(accountId, accountNumber, initialDeposit, currency);
        this.minimumBalance = MINIMUM_BALANCE;
        this.overdraftLimit = overdraftLimit;
        this.overdraftProtection = true;
    }

    @Override
    public String getAccountType() {
        return "CHECKING";
    }

    @Override
    public double calculateInterest() {
        return (getBalance() * INTEREST_RATE) / 100 / 12;
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }

    // Override withdraw to support overdraft
    @Override
    public boolean withdraw(double amount) {
        if (overdraftProtection && (getBalance() + overdraftLimit >= amount)) {
            double newBalance = getBalance() - amount;
            setBalance(newBalance);
            if (newBalance < 0) {
                System.out.println("WARNING: Account is in overdraft. Balance: " + newBalance);
            }
            return true;
        }
        return super.withdraw(amount);
    }

    public double getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(double limit) { this.overdraftLimit = limit; }
    public boolean hasOverdraftProtection() { return overdraftProtection; }
}
