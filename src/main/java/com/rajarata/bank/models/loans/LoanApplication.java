package com.rajarata.bank.models.loans;

public class LoanApplication {
    private String applicationId;

    public LoanApplication(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationId() { return applicationId; }
}
