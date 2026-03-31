package com.rajarata.bank;

import java.time.LocalDate;
import java.util.Scanner;

import com.rajarata.bank.exceptions.AuthenticationException;
import com.rajarata.bank.models.Customer;
import com.rajarata.bank.models.accounts.CheckingAccount;
import com.rajarata.bank.models.accounts.SavingsAccount;
import com.rajarata.bank.models.bills.Bill;
import com.rajarata.bank.models.loans.Loan;
import com.rajarata.bank.models.loans.LoanApplication;
import com.rajarata.bank.services.AuthenticationService;
import com.rajarata.bank.services.BillPaymentService;
import com.rajarata.bank.services.LoanService;
import com.rajarata.bank.services.NotificationService;
import com.rajarata.bank.services.TransactionService;

public class Main {
        private static final double SAVINGS_MINIMUM_BALANCE = 500.0;
        private static final double SAVINGS_WITHDRAWAL_LIMIT = 50000.0;

    public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);

        System.out.println("Rajarata Digital Banking started.");
        System.out.println("======================================================================================================================================");
        System.out.println("   ██████╗  █████╗      ██╗ █████╗ ██████╗  █████╗ ████████╗ █████╗     ██████╗ ██╗ ██████╗ ██╗████████╗ █████╗ ██╗     \r\n" + 
                                "██╔══██╗██╔══██╗     ██║██╔══██╗██╔══██╗██╔══██╗╚══██╔══╝██╔══██╗    ██╔══██╗██║██╔════╝ ██║╚══██╔══╝██╔══██╗██║     \r\n" + 
                                "██████╔╝███████║     ██║███████║██████╔╝███████║   ██║   ███████║    ██║  ██║██║██║  ███╗██║   ██║   ███████║██║     \r\n" + 
                                "██╔══██╗██╔══██║██   ██║██╔══██║██╔══██╗██╔══██║   ██║   ██╔══██║    ██║  ██║██║██║   ██║██║   ██║   ██╔══██║██║     \r\n" + 
                                "██║  ██║██║  ██║╚█████╔╝██║  ██║██║  ██║██║  ██║   ██║   ██║  ██║    ██████╔╝██║╚██████╔╝██║   ██║   ██║  ██║███████╗\r\n" + 
                                "╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝    ╚═════╝ ╚═╝ ╚═════╝ ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝\r\n" + 
                                "                                                                                                                     \r\n" + 
                                "██████╗  █████╗ ███╗   ██╗██╗  ██╗██╗███╗   ██╗ ██████╗     ███████╗██╗   ██╗███████╗████████╗███████╗███╗   ███╗    \r\n" + 
                                "██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝██║████╗  ██║██╔════╝     ██╔════╝╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔════╝████╗ ████║    \r\n" + 
                                "██████╔╝███████║██╔██╗ ██║█████╔╝ ██║██╔██╗ ██║██║  ███╗    ███████╗ ╚████╔╝ ███████╗   ██║   █████╗  ██╔████╔██║    \r\n" + 
                                "██╔══██╗██╔══██║██║╚██╗██║██╔═██╗ ██║██║╚██╗██║██║   ██║    ╚════██║  ╚██╔╝  ╚════██║   ██║   ██╔══╝  ██║╚██╔╝██║    \r\n" + 
                                "██████╔╝██║  ██║██║ ╚████║██║  ██╗██║██║ ╚████║╚██████╔╝    ███████║   ██║   ███████║   ██║   ███████╗██║ ╚═╝ ██║    \r\n" + 
                                "╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝     ╚══════╝   ╚═╝   ╚══════╝   ╚═╝   ╚══════╝╚═╝     ╚═╝        ");
        System.out.println("=======================================================================================================================================\n");

        // --- 1. User Registration & Authentication ---
        System.out.println("--- 1. User Registration & Login ---\n");
        AuthenticationService authService = new AuthenticationService();

                String userId = prompt(scanner, "Enter user ID", "U001");
                String username = prompt(scanner, "Enter username", "kamal");
                String password = prompt(scanner, "Enter password", "Kamal@2024!");
                String email = prompt(scanner, "Enter email", "kamal@example.com");
                String phone = prompt(scanner, "Enter phone", "0771234567");
                String customerId = prompt(scanner, "Enter customer ID", "C001");
                String address = prompt(scanner, "Enter address", "123 Colombo Road, Anuradhapura");

        Customer customer = new Customer(
                                userId, username, password, email,
                                phone, customerId, address
        );
                boolean registered = authService.register(customer, password);
        System.out.println("Registration result: " + (registered ? "SUCCESS" : "FAILED"));

                String loginUsername = prompt(scanner, "Login username", username);
                String loginPassword = prompt(scanner, "Login password", password);
        try {
                        authService.login(loginUsername, loginPassword);
            System.out.println("Current user: " + authService.getCurrentUser().getName());
        } catch (AuthenticationException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        // --- 2. Account Operations ---
        System.out.println("\n--- 2. Account Operations ---\n");
                String savingsId = prompt(scanner, "Enter savings account ID", "A001");
                String savingsNumber = prompt(scanner, "Enter savings account number", "SAV-1001");
                double savingsBalance = promptSavingsInitialBalance(scanner, 10000.0);

                String checkingId = prompt(scanner, "Enter checking account ID", "A002");
                String checkingNumber = prompt(scanner, "Enter checking account number", "CHK-2001");
                double checkingBalance = promptDouble(scanner, "Enter initial checking balance", 25000.0);
                double overdraftLimit = promptDouble(scanner, "Enter checking overdraft limit", 5000.0);

                SavingsAccount savings = new SavingsAccount(savingsId, savingsNumber, savingsBalance, "LKR");
                CheckingAccount checking = new CheckingAccount(checkingId, checkingNumber, checkingBalance, "LKR", overdraftLimit);
        customer.addAccount(savings);
        customer.addAccount(checking);
        System.out.println("Savings Account: " + savings.getAccountNumber()
                + " | Balance: " + savings.getBalance() + " " + savings.getCurrency());
        System.out.println("Checking Account: " + checking.getAccountNumber()
                + " | Balance: " + checking.getBalance() + " " + checking.getCurrency());

        // Deposit
                double depositAmount = promptDouble(scanner, "Enter deposit amount to savings", 5000.0);
                savings.deposit(depositAmount);
                System.out.println("\nDeposited " + String.format("%.2f", depositAmount) + " to savings  -> Balance: " + String.format("%.2f", savings.getBalance()));

        // Withdraw
                double withdrawAmount = promptDouble(scanner, "Enter withdrawal amount from checking", 3000.0);
                checking.withdraw(withdrawAmount);
                System.out.println("Withdrew " + String.format("%.2f", withdrawAmount) + " from checking -> Balance: " + String.format("%.2f", checking.getBalance()));

        // Transfer
                double transferAmount = promptSavingsTransferAmount(scanner, savings, 2000.0);
                boolean transferred = savings.transfer(checking, transferAmount);
                System.out.println("Transferred " + String.format("%.2f", transferAmount) + " savings->checking: " + (transferred ? "SUCCESS" : "FAILED"));
                System.out.println("  Savings balance:  " + String.format("%.2f", savings.getBalance()));
                System.out.println("  Checking balance: " + String.format("%.2f", checking.getBalance()));

        // Interest
        System.out.println("\nSavings interest rate: " + savings.getInterestRate() + "%");
        System.out.println("Monthly interest:     " + String.format("%.2f", savings.calculateInterest()));
        savings.applyInterest();
        System.out.println("Balance after interest: " + String.format("%.2f", savings.getBalance()));

        // --- 3. Loan Processing ---
        System.out.println("\n--- 3. Loan Processing ---\n");
        LoanService loanService = new LoanService();
                double loanAmount = promptDouble(scanner, "Enter loan amount", 500000.0);
                int loanTermMonths = promptInt(scanner, "Enter loan term (months)", 24);
                String loanPurpose = prompt(scanner, "Enter loan purpose", "Home renovation");
                double loanInterest = promptDouble(scanner, "Enter loan annual interest rate (%)", 12.0);

        LoanApplication application = loanService.applyForLoan(
                                customer, loanAmount, loanTermMonths, loanPurpose
        );
        System.out.println("Application status: " + application.getStatus());
                Loan loan = loanService.approveLoan(application, loanInterest);
        System.out.println("Application status: " + application.getStatus());
        System.out.println("Monthly payment:    " + String.format("%.2f", loan.getMonthlyPayment()));
        System.out.println("Loan status:        " + loan.getStatus());
        System.out.println("Next due date:      " + loan.getNextDueDate());

        // Make a payment
                double loanPayment = promptDouble(scanner, "Enter loan payment amount", loan.getMonthlyPayment());
                boolean paid = loan.makePayment(loanPayment);
        System.out.println("\nPayment made: " + (paid ? "SUCCESS" : "FAILED"));
        System.out.println("Remaining balance: " + String.format("%.2f", loan.getRemainingBalance()));

        // --- 4. Bill Payment ---
        System.out.println("\n--- 4. Bill Payment ---\n");
        TransactionService txService = new TransactionService();
        BillPaymentService billService = new BillPaymentService(txService);

                double electricAmount = promptDouble(scanner, "Enter electricity bill amount", 3500.0);
                int electricDueDays = promptInt(scanner, "Enter electricity bill due in days", 5);
                double waterAmount = promptDouble(scanner, "Enter water bill amount", 1200.0);
                int waterDueDays = promptInt(scanner, "Enter water bill due in days", 3);

                Bill electricBill = new Bill("BILL-001", "ELECTRICITY", "CEB",
                                "ELC-9876", electricAmount, LocalDate.now().plusDays(electricDueDays));
                Bill waterBill = new Bill("BILL-002", "WATER", "NWSDB",
                                "WTR-5432", waterAmount, LocalDate.now().plusDays(waterDueDays));
        billService.scheduleBillPayment(electricBill);
        billService.scheduleBillPayment(waterBill);
        System.out.println("Upcoming bills: " + billService.getUpcomingBills().size());
        boolean billPaid = billService.payBill(checking, electricBill);
        System.out.println("Electric bill paid: " + (billPaid ? "YES" : "NO"));
                System.out.println("Checking balance after bill: " + String.format("%.2f", checking.getBalance()));

        // --- 5. Notifications ---
        System.out.println("\n--- 5. Notifications ---\n");
        NotificationService notifService = new NotificationService();
                notifService.sendTransactionNotification(customer, "DEPOSIT", depositAmount, true);
        notifService.sendLoanInstallmentReminder(customer, loan);
                double lowBalanceThreshold = promptDouble(scanner, "Enter low-balance alert threshold", 20000.0);
                notifService.sendLowBalanceAlert(customer, savings, lowBalanceThreshold);
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
        System.out.println("  ████████╗██╗  ██╗ █████╗ ███╗   ██╗██╗  ██╗    ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗██╗\r\n" + //
                                "╚══██╔══╝██║  ██║██╔══██╗████╗  ██║██║ ██╔╝    ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║██║\r\n" + //
                                "   ██║   ███████║███████║██╔██╗ ██║█████╔╝      ╚████╔╝ ██║   ██║██║   ██║    ██║██║\r\n" + //
                                "   ██║   ██╔══██║██╔══██║██║╚██╗██║██╔═██╗       ╚██╔╝  ██║   ██║██║   ██║    ╚═╝╚═╝\r\n" + //
                                "   ██║   ██║  ██║██║  ██║██║ ╚████║██║  ██╗       ██║   ╚██████╔╝╚██████╔╝    ██╗██╗\r\n" + //
                                "   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝       ╚═╝    ╚═════╝  ╚═════╝     ╚═╝╚═╝");
        System.out.println("========================================");

                int feedback;
                while (true) {
                        System.out.print("Please rate this app from 1 to 10 to exit: ");
                        String input = scanner.nextLine().trim();
                        try {
                                feedback = Integer.parseInt(input);
                                if (feedback >= 1 && feedback <= 10) {
                                        break;
                                }
                                System.out.println("Feedback must be between 1 and 10.");
                        } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid whole number between 1 and 10.");
                        }
                }

                System.out.println("Thanks for your feedback: " + feedback + "/10");
                scanner.close();
        }

        private static String prompt(Scanner scanner, String message, String defaultValue) {
                System.out.print(message + " [" + defaultValue + "]: ");
                String input = scanner.nextLine().trim();
                return input.isEmpty() ? defaultValue : input;
        }

        private static double promptDouble(Scanner scanner, String message, double defaultValue) {
                while (true) {
                        String input = prompt(scanner, message, String.valueOf(defaultValue));
                        try {
                                return Double.parseDouble(input);
                        } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid decimal number.");
                        }
                }
        }

        private static double promptSavingsInitialBalance(Scanner scanner, double defaultValue) {
                while (true) {
                        double amount = promptDouble(scanner, "Enter initial savings balance", defaultValue);
                        if (amount < SAVINGS_MINIMUM_BALANCE) {
                                System.out.println("Savings initial balance must be at least " + SAVINGS_MINIMUM_BALANCE + ".");
                                continue;
                        }
                        return amount;
                }
        }

        private static double promptSavingsTransferAmount(Scanner scanner, SavingsAccount savings, double defaultValue) {
                while (true) {
                        double amount = promptDouble(scanner, "Enter transfer amount from savings to checking", defaultValue);
                        if (amount > SAVINGS_WITHDRAWAL_LIMIT) {
                                System.out.println("Savings transfer cannot exceed " + SAVINGS_WITHDRAWAL_LIMIT + " per transaction.");
                                continue;
                        }

                        double remainingBalance = savings.getBalance() - amount;
                        if (remainingBalance < SAVINGS_MINIMUM_BALANCE) {
                                System.out.println("Transfer would violate minimum savings balance of " + SAVINGS_MINIMUM_BALANCE + ".");
                                continue;
                        }
                        return amount;
                }
        }

        private static int promptInt(Scanner scanner, String message, int defaultValue) {
                while (true) {
                        String input = prompt(scanner, message, String.valueOf(defaultValue));
                        try {
                                return Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid whole number.");
                        }
                }
    }
}