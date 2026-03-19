package com.rajarata.bank.interfaces;

public interface Transactable {
    void deposit(double amount);
    void withdraw(double amount);
    boolean transfer(Transactable targetAccount, double amount);
    double getBalance();

}
