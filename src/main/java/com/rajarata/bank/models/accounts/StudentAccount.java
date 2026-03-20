package com.rajarata.bank.models.accounts;
import java.time.LocalDate;

public class StudentAccount extends Account {
    private static final double INTEREST_RATE = 3.0; // 3% annual interest
    private static final double MINIMUM_BALANCE = 0;
    private static final double WITHDRAWAL_LIMIT = 1000000.0;

    private String studentId;
    private String institutionName;
    private LocalDate graduationDate;

    public StudentAccount(String accountId, String accountNumber, double initialDeposit,
                          String currency, String studentId, String institutionName,
                          LocalDate graduationDate) {
        super(accountId, accountNumber, initialDeposit, currency);
        this.studentId = studentId;
        this.institutionName = institutionName;
        this.graduationDate = graduationDate;
        this.interestRate = INTEREST_RATE;
        this.minimumBalance = MINIMUM_BALANCE;
        this.withdrawalLimit = WITHDRAWAL_LIMIT;
    }

    @Override
    public String getAccountType() {
        return "STUDENT";
    }

        @Override
    public double calculateInterest() {
        return (getBalance() * INTEREST_RATE) / 100 / 12;
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }

    // Check if student account is still valid
    public boolean isStudentStatusValid() {
        return LocalDate.now().isBefore(graduationDate);
    }

    // Convert to savings account after graduation
    public SavingsAccount convertToSavingsAccount() {
        if (!isStudentStatusValid()) {
            return new SavingsAccount(getAccountId(), getAccountNumber(), 
                                      getBalance(), getCurrency());
        }
        return null;
    }



    public String getStudentId() {
        return studentId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }
}
