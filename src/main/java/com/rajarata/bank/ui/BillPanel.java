package com.rajarata.bank.ui;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.models.accounts.Account;
import com.rajarata.bank.models.bills.Bill;
import com.rajarata.bank.services.BillPaymentService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BillPanel extends JPanel {

    private final Customer customer;
    private final BillPaymentService billPaymentService;
    private JTextArea outputArea;
    private DefaultTableModel tableModel;

    public BillPanel(Customer customer, BillPaymentService billPaymentService) {
        this.customer = customer;
        this.billPaymentService = billPaymentService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 248, 255));
        buildUI();
    }

    private void buildUI() {
        JLabel title = new JLabel("Bill Payment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0, 70, 127));
        add(title, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(245, 248, 255));

        // Add Bill
        JPanel addBillPanel = buildTitledPanel("Add Bill");
        JTextField billIdField = new JTextField("BILL-001", 10);
        JTextField billTypeField = new JTextField("ELECTRICITY", 10);
        JTextField providerField = new JTextField("CEB", 10);
        JTextField referenceField = new JTextField("ELC-9876", 10);
        JTextField amountField = new JTextField("3500", 10);
        JTextField dueDaysField = new JTextField("5", 10);
        addBillPanel.add(labeledField("Bill ID:", billIdField));
        addBillPanel.add(labeledField("Type:", billTypeField));
        addBillPanel.add(labeledField("Provider:", providerField));
        addBillPanel.add(labeledField("Reference:", referenceField));
        addBillPanel.add(labeledField("Amount:", amountField));
        addBillPanel.add(labeledField("Due in days:", dueDaysField));
        JButton addBtn = actionButton("Add Bill");
        addBtn.addActionListener(e -> {
            try {
                String billId = billIdField.getText().trim();
                String type = billTypeField.getText().trim();
                String provider = providerField.getText().trim();
                String reference = referenceField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                int dueDays = Integer.parseInt(dueDaysField.getText().trim());
                if (billId.isEmpty() || type.isEmpty() || provider.isEmpty() || reference.isEmpty()) {
                    showError("All fields are required.");
                    return;
                }
                Bill bill = new Bill(billId, type, provider, reference, amount,
                        LocalDate.now().plusDays(dueDays));
                billPaymentService.scheduleBillPayment(bill);
                refreshTable();
                showResult("Bill added: " + type + " - " + provider + " | Amount: " + String.format("%.2f", amount));
            } catch (NumberFormatException ex) {
                showError("Invalid numeric value.");
            }
        });
        addBillPanel.add(addBtn);
        leftPanel.add(addBillPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Pay Bill
        JPanel payPanel = buildTitledPanel("Pay Bill");
        JTextField payAccField = new JTextField("", 10);
        JTextField payBillIdField = new JTextField("", 10);
        payPanel.add(labeledField("Account Number:", payAccField));
        payPanel.add(labeledField("Bill ID:", payBillIdField));
        JButton payBtn = actionButton("Pay Bill");
        payBtn.addActionListener(e -> {
            String accNum = payAccField.getText().trim();
            String billId = payBillIdField.getText().trim();
            if (accNum.isEmpty() || billId.isEmpty()) {
                showError("Account Number and Bill ID are required.");
                return;
            }
            Account acc = findAccount(accNum);
            if (acc == null) return;
            Bill bill = findBill(billId);
            if (bill == null) return;
            if (bill.isPaid()) {
                showError("Bill is already paid.");
                return;
            }
            boolean ok = billPaymentService.payBill(acc, bill);
            if (ok) {
                refreshTable();
                showResult("Bill paid successfully!\nBill: " + bill.getBillType()
                        + " | Amount: " + String.format("%.2f", bill.getAmount())
                        + "\nAccount Balance: " + String.format("%.2f", acc.getBalance()));
            } else {
                showError("Payment failed. Insufficient funds.");
            }
        });
        payPanel.add(payBtn);
        leftPanel.add(payPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // View Upcoming Bills button
        JPanel viewPanel = buildTitledPanel("View Bills");
        JButton viewBtn = actionButton("View Upcoming Bills");
        viewBtn.addActionListener(e -> {
            refreshTable();
            List<Bill> upcoming = billPaymentService.getUpcomingBills();
            showResult("Upcoming bills (due within 7 days): " + upcoming.size());
        });
        viewPanel.add(viewBtn);
        leftPanel.add(viewPanel);

        JScrollPane leftScroll = new JScrollPane(leftPanel);
        leftScroll.setBorder(BorderFactory.createEmptyBorder());
        leftScroll.setPreferredSize(new Dimension(360, 0));

        // Right: table + output
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBackground(new Color(245, 248, 255));

        String[] columns = {"Bill ID", "Type", "Provider", "Amount", "Due Date", "Paid"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable billTable = new JTable(tableModel);
        billTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        billTable.setRowHeight(24);
        billTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        billTable.getTableHeader().setBackground(new Color(0, 70, 127));
        billTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane tableScroll = new JScrollPane(billTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 70, 127), 1), "Bills"));

        outputArea = new JTextArea(4, 20);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(new Color(240, 244, 255));
        outputArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 70, 127), 1), "Output"));
        outputScroll.setPreferredSize(new Dimension(0, 100));

        rightPanel.add(tableScroll, BorderLayout.CENTER);
        rightPanel.add(outputScroll, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, rightPanel);
        splitPane.setDividerLocation(370);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        add(splitPane, BorderLayout.CENTER);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Bill bill : billPaymentService.getUpcomingBills()) {
            tableModel.addRow(new Object[]{
                    bill.getBillId(), bill.getBillType(), bill.getProvider(),
                    String.format("%.2f", bill.getAmount()), bill.getDueDate(), bill.isPaid() ? "Yes" : "No"
            });
        }
        // Also show paid/all bills - check overdue
        for (Bill bill : billPaymentService.getOverdueBills()) {
            tableModel.addRow(new Object[]{
                    bill.getBillId(), bill.getBillType(), bill.getProvider(),
                    String.format("%.2f", bill.getAmount()), bill.getDueDate() + " (OVERDUE)", bill.isPaid() ? "Yes" : "No"
            });
        }
    }

    private Bill findBill(String billId) {
        for (Bill bill : billPaymentService.getUpcomingBills()) {
            if (bill.getBillId().equals(billId)) return bill;
        }
        for (Bill bill : billPaymentService.getOverdueBills()) {
            if (bill.getBillId().equals(billId)) return bill;
        }
        showError("Bill not found: " + billId + ". Add the bill first.");
        return null;
    }

    private Account findAccount(String accountNumber) {
        for (Account acc : customer.getAccounts()) {
            if (acc.getAccountNumber().equals(accountNumber)) return acc;
        }
        showError("Account not found: " + accountNumber);
        return null;
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
        label.setPreferredSize(new Dimension(120, 20));
        row.add(label);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setPreferredSize(new Dimension(140, 26));
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
