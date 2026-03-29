package com.rajarata.bank.models.accounts;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class FixedDepositAccount extends Account {

     private double interestRate;
     private int termMonths;
     private LocalDateTime maturityDate;
     private double earlyWithdrawalPenaltyRate;
     private boolean isMatured;


    public FixedDepositAccount(String accountNumber, double initialBalance, String accountId,double depositAmount, String currency, double interestRate, int termMonths, double earlyWithdrawalPenaltyRate) {
        super(accountNumber, initialBalance);
        this.interestRate = interestRate;
        this.termMonths = termMonths;
        this.maturityDate = LocalDateTime.now().plusMonths(termMonths);
        this.earlyWithdrawalPenaltyRate = 2.0; // 2% penalty for early withdrawal
        this.isMatured = false;


    }

    @Override
    public String getAccountType() {
        return "FIXED_DEPOSIT";
    }

        @Override
    public double calculateInterest() {
        // Calculate total interest for the term
        return (getBalance() * interestRate * termMonths) / 100 / 12;
    }

     @Override
    public double getInterestRate() {
        return interestRate;
    }


@Override
public boolean withdraw(double amount){
        checkMaturity();

        if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
        }

        if (!isMatured){
                 // Early withdrawal closes the deposit after applying the penalty.
                 double penalty = getBalance() * (earlyWithdrawalPenaltyRate / 100);
                 double availableAmount = getBalance() - penalty;

                         System.out.println("EARLY WITHDRAWAL PENALTY: " + penalty + " " + getCurrency());
                        System.out.println("Amount available after penalty: " + availableAmount);

                        if(amount <= availableAmount){
                                 setBalance(0);
                                 setActive(false);
                                 System.out.println("Fixed Deposit closed early. Penalty applied.");
                                 return true;
                        }
                        throw new IllegalArgumentException("Requested amount exceeds amount available after penalty");

        }
            // Matured withdrawal closes the deposit with full balance available.
                if (amount > getBalance()) {
                        throw new IllegalArgumentException("Insufficient funds");
                }

                setBalance(0);
                setActive(false);
                System.out.println("Fixed Deposit closed after maturity.");
                return true;
}

private void checkMaturity() {
        if (LocalDateTime.now().isAfter(maturityDate)) {
        if(!isMatured){
              isMatured = true;
              applyInterest();
              System.out.println("Fixed Deposit matured. Interest applied.");
        }
        }


       
        }

        public LocalDateTime getMaturityDateTime(){
            return maturityDate;
        }

        public int getTermMonths() { return termMonths; }
        public boolean isMatured() { 
        checkMaturity();
        return isMatured; 
    }

public long getDaysUntilMaturity() {
        return ChronoUnit.DAYS.between(LocalDateTime.now(), maturityDate);
    }


}

