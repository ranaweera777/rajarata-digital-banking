package com.rajarata.bank.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IDGenerator {
    private static AtomicLong userCounter = new AtomicLong(1000);
    private static AtomicLong accountCounter = new AtomicLong(100000000);
    private static AtomicLong trnsactionCounter = new AtomicLong(1);




    public static String generateUserId() {
        return "USR" + userCounter.incrementAndGet();
    }

    public static String generateCustomerId(){
        return "CUS" + userCounter.incrementAndGet();
    }

      public static String generateAccountId() {
        return "ACC" + accountCounter.incrementAndGet();
    }

     public static String generateAccountNumber(){
        return String.format("%012d", accountCounter.incrementAndGet());
    }

        public static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + trnsactionCounter.incrementAndGet();
    }

       public static String generateLoanId() {
        return "LOAN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

        public static String generateBillId() {
        return "BILL" + System.currentTimeMillis();
    }






}
