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
   // did not implement bill class yet
   public boolean payBill(Account sourceAccount, Bill bill){
      if(sourceAccount.getBalance() < bill.getAmount()){
            System.out.println("Insufficient funds to pay bill: " + bill.getBillId());
            return false;
      }
      //modify bill class
      boolean success = sourceAccount.withdraw(bill.getAmount());
      if(success){
        bill.markAsPaid();
          System.out.println("Bill paid successfully: " + bill.getBillType()+ "-Amount: " + bill.getAmount());
     
          return true;
      }
      return false;
   }

   public void scheduleBillPayment(Bill bill){
       scheduledBills.add(bill);
       System.out.println("Bill scheduled for payment: " + bill.getBillId() + " Due: " + bill.getDueDate());
   }

       public List<Bill> getUpcomingBills() {
        List<Bill> upcoming = new ArrayList<>();
        for (Bill bill : scheduledBills) {
            if (!bill.isPaid() && bill.getDaysUntilDue() <= 7) {
                upcoming.add(bill);
            }
        }
        return upcoming;
    }

        public List<Bill> getOverdueBills() {
        List<Bill> overdue = new ArrayList<>();
        for (Bill bill : scheduledBills) {
            if (!bill.isPaid() && bill.isOverdue()) {
                overdue.add(bill);
            }
        }
        return overdue;
    }




}
    