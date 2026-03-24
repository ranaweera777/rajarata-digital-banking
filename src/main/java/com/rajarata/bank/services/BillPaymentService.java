package com.rajarata.bank.services;

import com.rajarata.bank.models.accounts.Account;
import com.rajarata.bank.models.bills.Bill;
import com.rajarata.bank.models.transactions.Transaction;
import com.rajarata.bank.utils.IDGenerator;
import java.util.ArrayList;
import java.util.List;


public class BillPaymentService {
   private List<Bill> scheduledBills;
   private TransactionService transactionService;

   public BillPaymentService(TransactionService transactionService) {
       this.scheduledBills = new ArrayList<>();
       this.transactionService = transactionService;
   }

   public boolean

}
