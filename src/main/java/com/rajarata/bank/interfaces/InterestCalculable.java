package com.rajarata.bank.interfaces;

public interface InterestCalculable {
    double calculateInterest();
    double getInterestRate();
    void setInterestRate(double interestRate);
    void applyInterest();
}
