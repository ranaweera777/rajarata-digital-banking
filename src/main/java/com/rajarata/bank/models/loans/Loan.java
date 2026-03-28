package com.rajarata.bank.models.loans;

import com.rajarata.bank.models.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    private String loanId;
    private Customer customer;
    private double principalAmount;
    private double interestRate;
    private int termMonths;
    private double monthlyPayment;
    private double remainBalance;
    private LocalDate startDate;
    private LocalDate nextDueDate;
    private String status;
    private List<LoanPayment> paymentHistory;
    private List<RepaymentScheduleEntry> repaymentSchedule;

    public Loan(String loanId, double principalAmount, Customer customer, double interestRate, int termMonths) {
        this.loanId = loanId;
        this.principalAmount = principalAmount;
        this.customer = customer;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
        this.remainBalance = principalAmount;
        this.startDate = LocalDate.now();
        this.nextDueDate = startDate.plusMonths(1);
        this.status = "ACTIVE";
        this.paymentHistory = new ArrayList<>();
        this.repaymentSchedule = new ArrayList<>();
        calculateMonthlyPayment();
    }

    private void calculateMonthlyPayment() {
        double monthlyRate = interestRate / 100 / 12;
        this.monthlyPayment = (principalAmount * monthlyRate * Math.pow(1 + monthlyRate, termMonths))
                / (Math.pow(1 + monthlyRate, termMonths) - 1);
    }

    public void generateRepaymentSchedule() {
        double balance = principalAmount;
        double monthlyRate = interestRate / 100 / 12;
        LocalDate dueDate = startDate.plusMonths(1);

        for (int month = 1; month <= termMonths; month++) {
            double interestPayment = balance * monthlyRate;
            double principalPayment = monthlyPayment - interestPayment;
            balance -= principalPayment;

            repaymentSchedule.add(new RepaymentScheduleEntry(month, dueDate, monthlyPayment, principalPayment,
                    interestPayment, Math.max(0, balance)));
            dueDate = dueDate.plusMonths(1);
        }
    }

    public boolean makePayment(double amount) {
        if (amount >= monthlyPayment) {
            double interestPortion = remainBalance * interestRate / 100 / 12;
            double principalPortion = amount - interestPortion;
            remainBalance -= principalPortion;
            paymentHistory.add(new LoanPayment(amount, LocalDate.now()));
            nextDueDate = nextDueDate.plusMonths(1);

            if (remainBalance <= 0) {
                status = "PAID OFF";
                remainBalance = 0;
                nextDueDate = null;
            }
            return true;
        }
        return false;
    }

    public String getLoanId() {
        return loanId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getRemainingBalance() {
        return remainBalance;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public String getStatus() {
        return status;
    }

    public List<RepaymentScheduleEntry> getRepaymentSchedule() {
        return repaymentSchedule;
    }

    public static class LoanPayment {
        private double amount;
        private LocalDate paymentDate;

        public LoanPayment(double amount, LocalDate paymentDate) {
            this.amount = amount;
            this.paymentDate = paymentDate;
        }
    }

public static class RepaymentScheduleEntry{
    private int month;
    private LocalDate dueDate;
    private double totalPayment;
    private double principalPayment;
    private double interestPayment;
    private double remainingBalance;

    public RepaymentScheduleEntry(int month, LocalDate dueDate, double totalPayment,
                                  double principalPayment, double interestPayment, double remainingBalance) {
        this.month = month;
        this.dueDate = dueDate;
        this.totalPayment = totalPayment;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.remainingBalance = remainingBalance;
    }



}


}