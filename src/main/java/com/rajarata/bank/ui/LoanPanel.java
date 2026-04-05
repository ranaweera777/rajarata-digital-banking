package com.rajarata.bank.ui;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.models.loans.Loan;
import com.rajarata.bank.models.loans.LoanApplication;
import com.rajarata.bank.services.LoanService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LoanPanel extends JPanel {

    private final Customer customer;
    private final LoanService loanService;
    private JTextArea outputArea;
    private LoanApplication currentApplication;
    private Loan currentLoan;

    public LoanPanel(Customer customer, LoanService loanService) {
        this.customer = customer;
        this.loanService = loanService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 248, 255));
        buildUI();
    }

    private void buildUI() {
        JLabel title = new JLabel("Loan Processing");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0, 70, 127));
        add(title, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(245, 248, 255));

        // Apply for Loan
        JPanel applyPanel = buildTitledPanel("Apply for Loan");
        JTextField loanAmtField = new JTextField("500000", 10);
        JTextField termField = new JTextField("24", 10);
        JTextField purposeField = new JTextField("Home renovation", 10);
        applyPanel.add(labeledField("Loan Amount:", loanAmtField));
        applyPanel.add(labeledField("Term (months):", termField));
        applyPanel.add(labeledField("Purpose:", purposeField));
        JButton applyBtn = actionButton("Apply for Loan");
        applyBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(loanAmtField.getText().trim());
                int term = Integer.parseInt(termField.getText().trim());
                String purpose = purposeField.getText().trim();
                if (purpose.isEmpty()) {
                    showError("Purpose is required.");
                    return;
                }
                currentApplication = loanService.applyForLoan(customer, amount, term, purpose);
                showResult("Loan Application Submitted!\n"
                        + "Application ID: " + currentApplication.getApplicationId() + "\n"
                        + "Amount: " + String.format("%.2f", amount) + "\n"
                        + "Term: " + term + " months\n"
                        + "Purpose: " + purpose + "\n"
                        + "Status: " + currentApplication.getStatus());
            } catch (NumberFormatException ex) {
                showError("Invalid numeric value.");
            }
        });
        applyPanel.add(applyBtn);
        leftPanel.add(applyPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Approve Loan
        JPanel approvePanel = buildTitledPanel("Approve Loan");
        JTextField interestField = new JTextField("12.0", 10);
        approvePanel.add(labeledField("Annual Interest Rate (%):", interestField));
        JButton approveBtn = actionButton("Approve Loan");
        approveBtn.addActionListener(e -> {
            if (currentApplication == null) {
                showError("No pending loan application. Please apply first.");
                return;
            }
            try {
                double rate = Double.parseDouble(interestField.getText().trim());
                currentLoan = loanService.approveLoan(currentApplication, rate);
                showResult("Loan Approved!\n"
                        + "Loan ID: " + currentLoan.getLoanId() + "\n"
                        + "Principal: " + String.format("%.2f", currentLoan.getPrincipalAmount()) + "\n"
                        + "Interest Rate: " + currentLoan.getInterestRate() + "%\n"
                        + "Monthly Payment: " + String.format("%.2f", currentLoan.getMonthlyPayment()) + "\n"
                        + "Status: " + currentLoan.getStatus() + "\n"
                        + "Next Due Date: " + currentLoan.getNextDueDate());
            } catch (NumberFormatException ex) {
                showError("Invalid interest rate.");
            }
        });
        approvePanel.add(approveBtn);
        leftPanel.add(approvePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Make Payment
        JPanel paymentPanel = buildTitledPanel("Make Loan Payment");
        JTextField paymentAmtField = new JTextField("", 10);
        paymentPanel.add(labeledField("Payment Amount:", paymentAmtField));
        JButton payBtn = actionButton("Make Payment");
        payBtn.addActionListener(e -> {
            if (currentLoan == null) {
                showError("No active loan. Please apply and approve a loan first.");
                return;
            }
            try {
                String amtText = paymentAmtField.getText().trim();
                double amt = amtText.isEmpty() ? currentLoan.getMonthlyPayment() : Double.parseDouble(amtText);
                boolean paid = currentLoan.makePayment(amt);
                if (paid) {
                    showResult("Payment of " + String.format("%.2f", amt) + " made successfully!\n"
                            + "Remaining Balance: " + String.format("%.2f", currentLoan.getRemainingBalance()) + "\n"
                            + "Loan Status: " + currentLoan.getStatus()
                            + (currentLoan.getNextDueDate() != null ? "\nNext Due Date: " + currentLoan.getNextDueDate() : "\n(Loan Paid Off!)"));
                } else {
                    showError("Payment failed. Amount must be at least the monthly payment of "
                            + String.format("%.2f", currentLoan.getMonthlyPayment()));
                }
            } catch (NumberFormatException ex) {
                showError("Invalid payment amount.");
            }
        });
        paymentPanel.add(payBtn);
        leftPanel.add(paymentPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Loan Status
        JPanel statusPanel = buildTitledPanel("Current Loan Status");
        JButton statusBtn = actionButton("Show Loan Status");
        statusBtn.addActionListener(e -> {
            if (currentLoan == null) {
                showResult("No active loan.");
                return;
            }
            showResult("--- Loan Status ---\n"
                    + "Loan ID: " + currentLoan.getLoanId() + "\n"
                    + "Principal: " + String.format("%.2f", currentLoan.getPrincipalAmount()) + "\n"
                    + "Monthly Payment: " + String.format("%.2f", currentLoan.getMonthlyPayment()) + "\n"
                    + "Remaining Balance: " + String.format("%.2f", currentLoan.getRemainingBalance()) + "\n"
                    + "Status: " + currentLoan.getStatus()
                    + (currentLoan.getNextDueDate() != null ? "\nNext Due Date: " + currentLoan.getNextDueDate() : ""));
        });
        statusPanel.add(statusBtn);
        leftPanel.add(statusPanel);

        JScrollPane leftScroll = new JScrollPane(leftPanel);
        leftScroll.setBorder(BorderFactory.createEmptyBorder());
        leftScroll.setPreferredSize(new Dimension(370, 0));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(new Color(240, 244, 255));
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 70, 127), 1), "Output"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, outputScroll);
        splitPane.setDividerLocation(380);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel buildTitledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 230), 1), title);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        border.setTitleColor(new Color(0, 70, 127));
        panel.setBorder(border);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return panel;
    }

    private JPanel labeledField(String labelText, JTextField field) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        row.setBackground(Color.WHITE);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setPreferredSize(new Dimension(200, 20));
        row.add(label);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setPreferredSize(new Dimension(130, 26));
        row.add(field);
        return row;
    }

    private JButton actionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(new Color(0, 70, 127));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void showResult(String msg) {
        outputArea.setText(msg);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
