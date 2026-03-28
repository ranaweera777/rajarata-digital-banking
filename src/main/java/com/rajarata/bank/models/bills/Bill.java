package com.rajarata.bank.models.bills;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Bill {
    private String billId;
    private double amount;
    private String billType;
    private String provider;
    private String accountNumber;
    private boolean isPaid;
    private LocalDate paidDate;
    private LocalDate dueDate;

    public Bill(String billId, String billType, String provider,
            String accountNumber, double amount, LocalDate dueDate) {
        this.billId = billId;
        this.billType = billType;
        this.provider = provider;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.dueDate = dueDate;
        this.isPaid = false;

    }

    public void markAsPaid() {
        this.isPaid = true;
        this.paidDate = LocalDate.now();
    }

    public boolean isOverdue() {
        return !isPaid && LocalDate.now().isAfter(dueDate);
    }

    public long getDaysUntilDue() {
        return ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
    }

    public double calculateLateFee() {
        if (isOverdue()) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            return amount * 0.02 * daysOverdue; // 2% per day late fee
        }
        return 0;
    }

    public String getBillId() {
        return billId;
    }

    public String getBillType() {
        return billType;
    }

    public String getProvider() {
        return provider;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }
}
