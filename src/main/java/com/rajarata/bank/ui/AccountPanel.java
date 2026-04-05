package com.rajarata.bank.ui;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.models.accounts.Account;
import com.rajarata.bank.models.accounts.CheckingAccount;
import com.rajarata.bank.models.accounts.SavingsAccount;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class AccountPanel extends JPanel {

    private static final double SAVINGS_MINIMUM_BALANCE = 500.0;
    private static final double SAVINGS_WITHDRAWAL_LIMIT = 50000.0;

    private final Customer customer;
    private JTextArea outputArea;

    public AccountPanel(Customer customer) {
        this.customer = customer;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 248, 255));
        buildUI();
    }

    private void buildUI() {
        JLabel title = new JLabel("Account Operations");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0, 70, 127));
        add(title, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(245, 248, 255));

        // Create Savings Account
        JPanel createSavingsPanel = buildTitledPanel("Create Savings Account");
        JTextField savingsIdField = new JTextField(10);
        JTextField savingsNumField = new JTextField(10);
        JTextField savingsBalField = new JTextField("10000", 10);
        createSavingsPanel.add(labeledField("Account ID:", savingsIdField));
        createSavingsPanel.add(labeledField("Account Number:", savingsNumField));
        createSavingsPanel.add(labeledField("Initial Balance (min " + SAVINGS_MINIMUM_BALANCE + "):", savingsBalField));
        JButton createSavingsBtn = actionButton("Create Savings");
        createSavingsBtn.addActionListener(e -> {
            try {
                String id = savingsIdField.getText().trim();
                String num = savingsNumField.getText().trim();
                double bal = Double.parseDouble(savingsBalField.getText().trim());
                if (id.isEmpty() || num.isEmpty()) {
                    showError("Account ID and Number are required.");
                    return;
                }
                if (bal < SAVINGS_MINIMUM_BALANCE) {
                    showError("Initial balance must be at least " + SAVINGS_MINIMUM_BALANCE);
                    return;
                }
                SavingsAccount acc = new SavingsAccount(id, num, bal, "LKR");
                customer.addAccount(acc);
                showResult("Savings account created: " + num + " | Balance: " + String.format("%.2f", bal));
            } catch (NumberFormatException ex) {
                showError("Invalid balance amount.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });
        createSavingsPanel.add(createSavingsBtn);
        leftPanel.add(createSavingsPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Create Checking Account
        JPanel createCheckingPanel = buildTitledPanel("Create Checking Account");
        JTextField checkIdField = new JTextField(10);
        JTextField checkNumField = new JTextField(10);
        JTextField checkBalField = new JTextField("25000", 10);
        JTextField overdraftField = new JTextField("5000", 10);
        createCheckingPanel.add(labeledField("Account ID:", checkIdField));
        createCheckingPanel.add(labeledField("Account Number:", checkNumField));
        createCheckingPanel.add(labeledField("Initial Balance:", checkBalField));
        createCheckingPanel.add(labeledField("Overdraft Limit:", overdraftField));
        JButton createCheckingBtn = actionButton("Create Checking");
        createCheckingBtn.addActionListener(e -> {
            try {
                String id = checkIdField.getText().trim();
                String num = checkNumField.getText().trim();
                double bal = Double.parseDouble(checkBalField.getText().trim());
                double overdraft = Double.parseDouble(overdraftField.getText().trim());
                if (id.isEmpty() || num.isEmpty()) {
                    showError("Account ID and Number are required.");
                    return;
                }
                CheckingAccount acc = new CheckingAccount(id, num, bal, "LKR", overdraft);
                customer.addAccount(acc);
                showResult("Checking account created: " + num + " | Balance: " + String.format("%.2f", bal));
            } catch (NumberFormatException ex) {
                showError("Invalid numeric value.");
            }
        });
        createCheckingPanel.add(createCheckingBtn);
        leftPanel.add(createCheckingPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // View Balances
        JPanel viewPanel = buildTitledPanel("View Balances");
        JButton viewBtn = actionButton("View All Accounts");
        viewBtn.addActionListener(e -> {
            List<Account> accounts = customer.getAccounts();
            if (accounts.isEmpty()) {
                showResult("No accounts found.");
                return;
            }
            StringBuilder sb = new StringBuilder("--- Account Balances ---\n");
            for (Account acc : accounts) {
                sb.append(acc.getAccountType()).append(" | ").append(acc.getAccountNumber())
                        .append(" | Balance: ").append(String.format("%.2f", acc.getBalance()))
                        .append(" ").append(acc.getCurrency()).append("\n");
            }
            showResult(sb.toString());
        });
        viewPanel.add(viewBtn);
        leftPanel.add(viewPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Deposit
        JPanel depositPanel = buildTitledPanel("Deposit");
        JTextField depositAccField = new JTextField(10);
        JTextField depositAmtField = new JTextField("1000", 10);
        depositPanel.add(labeledField("Account Number:", depositAccField));
        depositPanel.add(labeledField("Amount:", depositAmtField));
        JButton depositBtn = actionButton("Deposit");
        depositBtn.addActionListener(e -> {
            Account acc = findAccount(depositAccField.getText().trim());
            if (acc == null) return;
            try {
                double amt = Double.parseDouble(depositAmtField.getText().trim());
                acc.deposit(amt);
                showResult("Deposited " + String.format("%.2f", amt) + " to " + acc.getAccountNumber()
                        + " | New Balance: " + String.format("%.2f", acc.getBalance()));
            } catch (NumberFormatException ex) {
                showError("Invalid amount.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });
        depositPanel.add(depositBtn);
        leftPanel.add(depositPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Withdraw
        JPanel withdrawPanel = buildTitledPanel("Withdraw");
        JTextField withdrawAccField = new JTextField(10);
        JTextField withdrawAmtField = new JTextField("500", 10);
        withdrawPanel.add(labeledField("Account Number:", withdrawAccField));
        withdrawPanel.add(labeledField("Amount:", withdrawAmtField));
        JButton withdrawBtn = actionButton("Withdraw");
        withdrawBtn.addActionListener(e -> {
            Account acc = findAccount(withdrawAccField.getText().trim());
            if (acc == null) return;
            try {
                double amt = Double.parseDouble(withdrawAmtField.getText().trim());
                acc.withdraw(amt);
                showResult("Withdrew " + String.format("%.2f", amt) + " from " + acc.getAccountNumber()
                        + " | New Balance: " + String.format("%.2f", acc.getBalance()));
            } catch (NumberFormatException ex) {
                showError("Invalid amount.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });
        withdrawPanel.add(withdrawBtn);
        leftPanel.add(withdrawPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Transfer
        JPanel transferPanel = buildTitledPanel("Transfer (Savings → Checking)");
        JTextField fromAccField = new JTextField(10);
        JTextField toAccField = new JTextField(10);
        JTextField transferAmtField = new JTextField("1000", 10);
        transferPanel.add(labeledField("From Account:", fromAccField));
        transferPanel.add(labeledField("To Account:", toAccField));
        transferPanel.add(labeledField("Amount:", transferAmtField));
        JButton transferBtn = actionButton("Transfer");
        transferBtn.addActionListener(e -> {
            Account from = findAccount(fromAccField.getText().trim());
            Account to = findAccount(toAccField.getText().trim());
            if (from == null || to == null) return;
            try {
                double amt = Double.parseDouble(transferAmtField.getText().trim());
                if (from instanceof SavingsAccount) {
                    if (amt > SAVINGS_WITHDRAWAL_LIMIT) {
                        showError("Transfer exceeds savings withdrawal limit of " + SAVINGS_WITHDRAWAL_LIMIT);
                        return;
                    }
                    if (from.getBalance() - amt < SAVINGS_MINIMUM_BALANCE) {
                        showError("Transfer would violate minimum savings balance of " + SAVINGS_MINIMUM_BALANCE);
                        return;
                    }
                }
                boolean ok = from.transfer(to, amt);
                if (ok) {
                    showResult("Transferred " + String.format("%.2f", amt) + " from " + from.getAccountNumber()
                            + " to " + to.getAccountNumber()
                            + "\nFrom Balance: " + String.format("%.2f", from.getBalance())
                            + "\nTo Balance: " + String.format("%.2f", to.getBalance()));
                } else {
                    showError("Transfer failed. Check balances.");
                }
            } catch (NumberFormatException ex) {
                showError("Invalid amount.");
            }
        });
        transferPanel.add(transferBtn);
        leftPanel.add(transferPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Apply Interest
        JPanel interestPanel = buildTitledPanel("Apply Interest");
        JTextField interestAccField = new JTextField(10);
        interestPanel.add(labeledField("Account Number:", interestAccField));
        JButton interestBtn = actionButton("Apply Interest");
        interestBtn.addActionListener(e -> {
            Account acc = findAccount(interestAccField.getText().trim());
            if (acc == null) return;
            double before = acc.getBalance();
            acc.applyInterest();
            double interest = acc.getBalance() - before;
            showResult("Interest applied to " + acc.getAccountNumber()
                    + "\nInterest: " + String.format("%.2f", interest)
                    + "\nNew Balance: " + String.format("%.2f", acc.getBalance()));
        });
        interestPanel.add(interestBtn);
        leftPanel.add(interestPanel);

        JScrollPane leftScroll = new JScrollPane(leftPanel);
        leftScroll.setBorder(BorderFactory.createEmptyBorder());
        leftScroll.setPreferredSize(new Dimension(380, 0));

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(new Color(240, 244, 255));
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 70, 127), 1), "Output"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, outputScroll);
        splitPane.setDividerLocation(390);
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
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 32));
        return btn;
    }

    private Account findAccount(String accountNumber) {
        if (accountNumber.isEmpty()) {
            showError("Account number cannot be empty.");
            return null;
        }
        for (Account acc : customer.getAccounts()) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        showError("Account not found: " + accountNumber);
        return null;
    }

    private void showResult(String msg) {
        outputArea.setText(msg);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
