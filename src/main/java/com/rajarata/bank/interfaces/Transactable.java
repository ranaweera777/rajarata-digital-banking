package com.rajarata.bank.interfaces;

public interface Transactable {
    boolean deposit(double amount);
    boolean withdraw(double amount);
    boolean transfer(Transactable targetAccount, double amount);
    double getBalance();

}
