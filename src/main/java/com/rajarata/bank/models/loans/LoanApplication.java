package com.rajarata.bank.models.loans;

import com.rajarata.bank.models.Customer;
import java.time.LocalDate;


public class LoanApplication {
    private String applicationId;
    private Customer customer;
    private double amount;
    private int termMonths;
    private String purpose;
    private String status;
    private LocalDate applicationDate;
    private String rejectionReason;

    public LoanApplication(String applicationId, Customer customer, double amount, int termMonths, String purpose) {
        this.applicationId = applicationId;
        this.customer = customer;
        this.amount = amount;
        this.termMonths = termMonths;
        this.purpose = purpose;
        this.status = "PENDING";
        this.applicationDate = LocalDate.now();
        this.rejectionReason = null;
    }

    public void approve() {
        this.status = "APPROVED";
    }

    public void reject(String reason) {
        this.status = "REJECTED";
        this.rejectionReason = reason;

    }

    public String getApplicationId() {
        return applicationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getAmount() {
        return amount;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }
}
