package com.rajarata.bank.services;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.models.accounts.Account;
import com.rajarata.bank.models.loans.Loan;
import com.rajarata.bank.models.bills.Bill;
import java.util.List;

public class NotificationService {

    public void sendLowBalanceAlert(Customer customer, Account account, double threshold){
         if(accouunt.getBalance() < threshold){
            String message = String.format( "LOW BALANCE ALERT: Your %s account (%s) balance is %.2f. " +
                "Please deposit funds to avoid service interruption.",
                account.getAccountType(), account.getAccountNumber(), account.getBalance());
             
                customer.sendAlert("LOW_BALANCE", message);
         }
    }

       public void sendTransactionNotification(Customer customer, String transactionType, 
                                            double amount, boolean success) {
        String status = success ? "successful" : "failed";
        String message = String.format(
            "Transaction %s: %s of %.2f was %s.",
            transactionType, transactionType.toLowerCase(), amount, status
        );
        customer.sendNotification(message);
    }

        public void sendLoanInstallmentReminder(Customer customer, Loan loan) {
        String message = String.format(
            "REMINDER: Your loan installment of %.2f is due on %s. " +
            "Please ensure sufficient balance in your account.",
            loan.getMonthlyPayment(), loan.getNextDueDate()
        );
        customer.sendAlert("LOAN_REMINDER", message);
    }

        public void sendBillPaymentReminder(Customer customer, List<Bill> upcomingBills) {
        for (Bill bill : upcomingBills) {
            String message = String.format(
                "BILL REMINDER: Your %s bill of %.2f is due on %s (%d days remaining).",
                bill.getBillType(), bill.getAmount(), bill.getDueDate(), bill.getDaysUntilDue()
            );
            customer.sendAlert("BILL_REMINDER", message);
        }
    }

       public void sendFailedTransactionAlert(Customer customer, String reason) {
        customer.sendAlert("TRANSACTION_FAILED", "Transaction failed: " + reason);
    }

    public void sendSecurityAlert(Customer customer, String event) {
        customer.sendAlert("SECURITY", "Security event detected: " + event);
    }


    
    
}
