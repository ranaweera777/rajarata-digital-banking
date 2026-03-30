package com.rajarata.bank;

import com.rajarata.bank.exceptions.AuthenticationException;
import com.rajarata.bank.models.Customer;
import com.rajarata.bank.models.accounts.SavingsAccount;
import com.rajarata.bank.models.accounts.CheckingAccount;
import com.rajarata.bank.models.bills.Bill;
import com.rajarata.bank.models.loans.Loan;
import com.rajarata.bank.models.loans.LoanApplication;
import com.rajarata.bank.services.AuthenticationService;
import com.rajarata.bank.services.BillPaymentService;
import com.rajarata.bank.services.LoanService;
import com.rajarata.bank.services.NotificationService;
import com.rajarata.bank.services.TransactionService;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // System.out.println("Rajarata Digital Banking started.");

        System.out.println("========================================");
        System.out.println("   Rajarata Digital Banking System");
        System.out.println("========================================\n");
        // --- 1. User Registration & Authentication ---
        System.out.println("--- 1. User Registration & Login ---\n");
        AuthenticationService authService = new AuthenticationService();
        Customer customer = new Customer(
                "U001", "kamal", "Kamal@2024!", "kamal@example.com",
                "0771234567", "C001", "123 Colombo Road, Anuradhapura"
        );
        boolean registered = authService.register(customer, "Kamal@2024!");
        System.out.println("Registration result: " + (registered ? "SUCCESS" : "FAILED"));
        try {
            authService.login("kamal", "Kamal@2024!");
            System.out.println("Current user: " + authService.getCurrentUser().getName());
        } catch (AuthenticationException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        // --- 2. Account Operations ---
        System.out.println("\n--- 2. Account Operations ---\n");
        SavingsAccount savings = new SavingsAccount("A001", "SAV-1001", 10000.0, "LKR");
        CheckingAccount checking = new CheckingAccount("A002", "CHK-2001", 25000.0, "LKR", 5000.0);
        customer.addAccount(savings);
        customer.addAccount(checking);
        System.out.println("Savings Account: " + savings.getAccountNumber()
                + " | Balance: " + savings.getBalance() + " " + savings.getCurrency());
        System.out.println("Checking Account: " + checking.getAccountNumber()
                + " | Balance: " + checking.getBalance() + " " + checking.getCurrency());
        // Deposit
        savings.deposit(5000);
        System.out.println("\nDeposited 5000 to savings  -> Balance: " + savings.getBalance());
        // Withdraw
        checking.withdraw(3000);
        System.out.println("Withdrew 3000 from checking -> Balance: " + checking.getBalance());
        // Transfer
        boolean transferred = savings.transfer(checking, 2000);
        System.out.println("Transferred 2000 savings->checking: " + (transferred ? "SUCCESS" : "FAILED"));
        System.out.println("  Savings balance:  " + savings.getBalance());
        System.out.println("  Checking balance: " + checking.getBalance());
        // Interest
        System.out.println("\nSavings interest rate: " + savings.getInterestRate() + "%");
        System.out.println("Monthly interest:     " + String.format("%.2f", savings.calculateInterest()));
        savings.applyInterest();
        System.out.println("Balance after interest: " + String.format("%.2f", savings.getBalance()));
        // --- 3. Loan Processing ---
        System.out.println("\n--- 3. Loan Processing ---\n");
        LoanService loanService = new LoanService();
        LoanApplication application = loanService.applyForLoan(
                customer, 500000, 24, "Home renovation"
        );
        System.out.println("Application status: " + application.getStatus());
        Loan loan = loanService.approveLoan(application, 12.0);
        System.out.println("Application status: " + application.getStatus());
        System.out.println("Monthly payment:    " + String.format("%.2f", loan.getMonthlyPayment()));
        System.out.println("Loan status:        " + loan.getStatus());
        System.out.println("Next due date:      " + loan.getNextDueDate());
        // Make a payment
        boolean paid = loan.makePayment(loan.getMonthlyPayment());
        System.out.println("\nPayment made: " + (paid ? "SUCCESS" : "FAILED"));
        System.out.println("Remaining balance: " + String.format("%.2f", loan.getRemainingBalance()));
        // --- 4. Bill Payment ---
        System.out.println("\n--- 4. Bill Payment ---\n");
        TransactionService txService = new TransactionService();
        BillPaymentService billService = new BillPaymentService(txService);
        Bill electricBill = new Bill("BILL-001", "ELECTRICITY", "CEB",
                "ELC-9876", 3500.0, LocalDate.now().plusDays(5));
        Bill waterBill = new Bill("BILL-002", "WATER", "NWSDB",
                "WTR-5432", 1200.0, LocalDate.now().plusDays(3));
        billService.scheduleBillPayment(electricBill);
        billService.scheduleBillPayment(waterBill);
        System.out.println("Upcoming bills: " + billService.getUpcomingBills().size());
        boolean billPaid = billService.payBill(checking, electricBill);
        System.out.println("Electric bill paid: " + (billPaid ? "YES" : "NO"));
        System.out.println("Checking balance after bill: " + checking.getBalance());
        // --- 5. Notifications ---
        System.out.println("\n--- 5. Notifications ---\n");
        NotificationService notifService = new NotificationService();
        notifService.sendTransactionNotification(customer, "DEPOSIT", 5000, true);
        notifService.sendLoanInstallmentReminder(customer, loan);
        notifService.sendLowBalanceAlert(customer, savings, 20000);
        System.out.println("\nAll notifications for " + customer.getName() + ":");
        for (String n : customer.getNotifications()) {
            System.out.println("  -> " + n);
        }
        // --- 6. Monthly Statement ---
        System.out.println("\n--- 6. Monthly Statement ---\n");
        System.out.println(savings.generateMonthlyStatement());
        // --- Logout ---
        authService.logout();
        System.out.println("========================================");
        System.out.println("   Demo complete. Thank you!");
        System.out.println("========================================");
    }
}
