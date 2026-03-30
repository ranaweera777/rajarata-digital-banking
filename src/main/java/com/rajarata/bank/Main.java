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
import java.util.List;
import java.util.Scanner;

public class Main {

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("   Rajarata Digital Banking System");
        System.out.println("========================================\n");

        // ─── 1. Customer Registration ───
        System.out.println("--- 1. Customer Registration ---\n");

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(userId, username, password, email, phone, customerId, address);

        AuthenticationService authService = new AuthenticationService();
        boolean registered = authService.register(customer, password);
        System.out.println("Registration result: " + (registered ? "SUCCESS" : "FAILED"));

        // ─── 2. Login ───
        System.out.println("\n--- 2. Login ---\n");

        System.out.print("Enter Username to login: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter Password to login: ");
        String loginPass = scanner.nextLine();

        try {
            authService.login(loginUser, loginPass);
            System.out.println("Logged-in user : " + authService.getCurrentUser().getName());
            System.out.println("Customer name  : " + customer.getName());
            System.out.println("Customer ID    : " + customer.getCustomerId());
        } catch (AuthenticationException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        // ─── 3. Link Accounts ───
        System.out.println("\n--- 3. Link Accounts to Customer ---\n");

        SavingsAccount savings = new SavingsAccount("A001", "SAV-1001", 10000.0, "LKR");
        CheckingAccount checking = new CheckingAccount("A002", "CHK-2001", 25000.0, "LKR", 5000.0);
        customer.addAccount(savings);
        customer.addAccount(checking);

        System.out.printf("Savings  -> Account: %s | Balance: %.2f %s%n",
                savings.getAccountNumber(), savings.getBalance(), savings.getCurrency());
        System.out.printf("Checking -> Account: %s | Balance: %.2f %s%n",
                checking.getAccountNumber(), checking.getBalance(), checking.getCurrency());

        // ─── 4. Deposit / Withdraw / Transfer ───
        System.out.println("\n--- 4. Account Operations ---\n");

        double depositAmt = readDouble(scanner, "Enter amount to deposit into Savings: ");
        savings.deposit(depositAmt);
        System.out.printf("Deposited %.2f to savings  -> Balance: %.2f%n", depositAmt, savings.getBalance());

        double withdrawAmt = readDouble(scanner, "Enter amount to withdraw from Checking: ");
        checking.withdraw(withdrawAmt);
        System.out.printf("Withdrew %.2f from checking -> Balance: %.2f%n", withdrawAmt, checking.getBalance());

        double transferAmt = readDouble(scanner, "Enter amount to transfer from Savings to Checking: ");
        boolean transferred = savings.transfer(checking, transferAmt);
        System.out.println("Transfer " + (transferred ? "SUCCESS" : "FAILED"));
        System.out.printf("  Savings balance:  %.2f%n", savings.getBalance());
        System.out.printf("  Checking balance: %.2f%n", checking.getBalance());

        // ─── 5. Interest & Monthly Statement ───
        System.out.println("\n--- 5. Interest & Monthly Statement ---\n");

        System.out.printf("Savings interest rate: %.2f%%%n", savings.getInterestRate());
        double monthlyInterest = savings.calculateInterest();
        System.out.printf("Monthly interest:      %.2f%n", monthlyInterest);
        savings.applyInterest();
        System.out.printf("Balance after interest: %.2f%n", savings.getBalance());
        System.out.println();
        System.out.println(savings.generateMonthlyStatement());

        // ─── 6. Loan Processing ───
        System.out.println("--- 6. Loan Processing ---\n");

        LoanService loanService = new LoanService();

        double loanAmount = readDouble(scanner, "Enter loan amount: ");
        int loanTerm = readInt(scanner, "Enter loan term (months): ");
        System.out.print("Enter loan purpose: ");
        String loanPurpose = scanner.nextLine();

        LoanApplication application = loanService.applyForLoan(customer, loanAmount, loanTerm, loanPurpose);
        System.out.println("Application status: " + application.getStatus());

        double interestRate = readDouble(scanner, "Enter interest rate to approve loan (%): ");
        Loan loan = loanService.approveLoan(application, interestRate);

        System.out.println("Application status : " + application.getStatus());
        System.out.printf("Monthly installment: %.2f%n", loan.getMonthlyPayment());
        System.out.println("Loan status        : " + loan.getStatus());
        System.out.println("Next due date      : " + loan.getNextDueDate());

        double paymentAmt = readDouble(scanner, "\nEnter payment amount for loan: ");
        boolean paid = loan.makePayment(paymentAmt);
        System.out.println("Payment made: " + (paid ? "SUCCESS" : "FAILED"));
        System.out.printf("Remaining balance: %.2f%n", loan.getRemainingBalance());

        // ─── 7. Bill Payment ───
        System.out.println("\n--- 7. Bill Payment ---\n");

        TransactionService txService = new TransactionService();
        BillPaymentService billService = new BillPaymentService(txService);

        int electricDueDays = readInt(scanner, "Enter due days for electricity bill: ");
        int waterDueDays = readInt(scanner, "Enter due days for water bill: ");

        Bill electricBill = new Bill("BILL-001", "ELECTRICITY", "CEB",
                "ELC-9876", 3500.0, LocalDate.now().plusDays(electricDueDays));
        Bill waterBill = new Bill("BILL-002", "WATER", "NWSDB",
                "WTR-5432", 1200.0, LocalDate.now().plusDays(waterDueDays));

        billService.scheduleBillPayment(electricBill);
        billService.scheduleBillPayment(waterBill);

        System.out.printf("Electricity bill due date: %s%n", electricBill.getDueDate());
        System.out.printf("Water bill due date:       %s%n", waterBill.getDueDate());

        List<Bill> upcoming = billService.getUpcomingBills();
        System.out.println("\nUpcoming unpaid bills: " + upcoming.size());
        for (Bill b : upcoming) {
            System.out.printf("  %s — %s — %.2f — Due: %s%n",
                    b.getBillId(), b.getBillType(), b.getAmount(), b.getDueDate());
        }

        boolean billPaid = billService.payBill(checking, electricBill);
        System.out.println("\nElectricity bill paid: " + (billPaid ? "YES" : "NO"));
        System.out.printf("Checking balance after bill: %.2f%n", checking.getBalance());

        // ─── 8. Notifications ───
        System.out.println("\n--- 8. Notifications ---\n");

        NotificationService notifService = new NotificationService();
        notifService.sendTransactionNotification(customer, "DEPOSIT", depositAmt, true);
        notifService.sendLoanInstallmentReminder(customer, loan);
        notifService.sendLowBalanceAlert(customer, savings, 50000);
        notifService.sendBillPaymentReminder(customer, billService.getUpcomingBills());

        System.out.println("\nAll notifications for " + customer.getName() + ":");
        for (String n : customer.getNotifications()) {
            System.out.println("  -> " + n);
        }

        // ─── 9. Logout ───
        System.out.println("\n--- 9. Logout ---\n");
        authService.logout();

        System.out.println("\n========================================");
        System.out.println("   Demo complete. Thank you!");
        System.out.println("========================================");

        scanner.close();
    }
}
