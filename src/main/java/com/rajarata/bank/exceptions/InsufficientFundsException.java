package com.rajarata.bank.exceptions;

public class InsufficientFundsException extends RuntimeException {
   private double availableBalance;
   private double requestedAmount;



    public InsufficientFundsException(String message, double availableBalance, double requestedAmount) {
        super(message);
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getShortfall(){
        return requestedAmount - availableBalance;
    }
}
    