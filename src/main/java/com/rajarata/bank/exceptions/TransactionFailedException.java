package com.rajarata.bank.exceptions;

public class TransactionFailedException extends RuntimeException {
   private String transactionId;
   private String reason;



    public TransactionFailedException(String message, String transactionId, String reason) {
        super(message);
        this.transactionId = transactionId;
        this.reason = reason;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getReason() {
        return reason;
    }   
    }

