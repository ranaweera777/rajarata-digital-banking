package com.rajarata.bank.ui;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.models.accounts.Account;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatementPanel extends JPanel {

    private final Customer customer;
    private JComboBox<String> accountCombo;
    private JTextArea statementArea;

    public StatementPanel(Customer customer) {
        this.customer = customer;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 248, 255));
        buildUI();
    }

    private void buildUI() {
        JLabel title = new JLabel("Monthly Statement");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0, 70, 127));
        add(title, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.setBackground(new Color(245, 248, 255));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel selectLabel = new JLabel("Select Account:");
        selectLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        controlPanel.add(selectLabel);

        accountCombo = new JComboBox<>();
        accountCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        accountCombo.setPreferredSize(new Dimension(250, 30));
        controlPanel.add(accountCombo);

        JButton refreshAccountsBtn = new JButton("Refresh Accounts");
        refreshAccountsBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        refreshAccountsBtn.setBackground(new Color(80, 120, 180));
        refreshAccountsBtn.setForeground(Color.WHITE);
        refreshAccountsBtn.setFocusPainted(false);
        refreshAccountsBtn.setBorderPainted(false);
        refreshAccountsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshAccountsBtn.addActionListener(e -> populateAccounts());
        controlPanel.add(refreshAccountsBtn);

        JButton generateBtn = new JButton("Generate Statement");
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        generateBtn.setBackground(new Color(0, 70, 127));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFocusPainted(false);
        generateBtn.setBorderPainted(false);
        generateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        generateBtn.addActionListener(e -> generateStatement());
        controlPanel.add(generateBtn);

        add(controlPanel, BorderLayout.NORTH);

        statementArea = new JTextArea();
        statementArea.setEditable(false);
        statementArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        statementArea.setBackground(new Color(250, 252, 255));
        statementArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        statementArea.setText("Select an account and click 'Generate Statement' to view the monthly statement.");

        JScrollPane scrollPane = new JScrollPane(statementArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 70, 127), 1), "Statement"));
        add(scrollPane, BorderLayout.CENTER);

        populateAccounts();
    }

    private void populateAccounts() {
        accountCombo.removeAllItems();
        List<Account> accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            accountCombo.addItem("No accounts found");
        } else {
            for (Account acc : accounts) {
                accountCombo.addItem(acc.getAccountType() + " - " + acc.getAccountNumber());
            }
        }
    }

    private void generateStatement() {
        List<Account> accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts found. Please create an account first.",
                    "No Accounts", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int selectedIndex = accountCombo.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= accounts.size()) {
            JOptionPane.showMessageDialog(this, "Please select a valid account.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Account acc = accounts.get(selectedIndex);
        String statement = acc.generateMonthlyStatement();
        statementArea.setText(statement);
        statementArea.setCaretPosition(0);
    }
}
