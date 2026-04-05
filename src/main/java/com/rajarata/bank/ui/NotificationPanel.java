package com.rajarata.bank.ui;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.models.accounts.Account;
import com.rajarata.bank.services.NotificationService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class NotificationPanel extends JPanel {

    private final Customer customer;
    private final NotificationService notificationService;
    private JTextArea notifArea;

    public NotificationPanel(Customer customer, NotificationService notificationService) {
        this.customer = customer;
        this.notificationService = notificationService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 248, 255));
        buildUI();
    }

    private void buildUI() {
        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(0, 70, 127));
        add(title, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(245, 248, 255));

        // Trigger transaction notification
        JPanel txPanel = buildTitledPanel("Transaction Notification");
        JTextField txTypeField = new JTextField("DEPOSIT", 10);
        JTextField txAmtField = new JTextField("5000", 10);
        txPanel.add(labeledField("Type:", txTypeField));
        txPanel.add(labeledField("Amount:", txAmtField));
        JButton txBtn = actionButton("Send Transaction Notification");
        txBtn.addActionListener(e -> {
            try {
                String type = txTypeField.getText().trim();
                double amt = Double.parseDouble(txAmtField.getText().trim());
                notificationService.sendTransactionNotification(customer, type, amt, true);
                refreshNotifications();
            } catch (NumberFormatException ex) {
                showError("Invalid amount.");
            }
        });
        txPanel.add(txBtn);
        leftPanel.add(txPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Low balance alert
        JPanel lowBalPanel = buildTitledPanel("Low Balance Alert");
        JTextField accNumField = new JTextField("", 10);
        JTextField thresholdField = new JTextField("20000", 10);
        lowBalPanel.add(labeledField("Account Number:", accNumField));
        lowBalPanel.add(labeledField("Threshold:", thresholdField));
        JButton lowBalBtn = actionButton("Send Low Balance Alert");
        lowBalBtn.addActionListener(e -> {
            try {
                String accNum = accNumField.getText().trim();
                double threshold = Double.parseDouble(thresholdField.getText().trim());
                Account acc = findAccount(accNum);
                if (acc == null) return;
                notificationService.sendLowBalanceAlert(customer, acc, threshold);
                refreshNotifications();
            } catch (NumberFormatException ex) {
                showError("Invalid threshold.");
            }
        });
        lowBalPanel.add(lowBalBtn);
        leftPanel.add(lowBalPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Security alert
        JPanel secPanel = buildTitledPanel("Security Alert");
        JTextField secEventField = new JTextField("Login from new device", 10);
        secPanel.add(labeledField("Event:", secEventField));
        JButton secBtn = actionButton("Send Security Alert");
        secBtn.addActionListener(e -> {
            String event = secEventField.getText().trim();
            if (event.isEmpty()) {
                showError("Event description required.");
                return;
            }
            notificationService.sendSecurityAlert(customer, event);
            refreshNotifications();
        });
        secPanel.add(secBtn);
        leftPanel.add(secPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // View all notifications
        JPanel viewPanel = buildTitledPanel("Refresh");
        JButton viewBtn = actionButton("Refresh Notifications");
        viewBtn.addActionListener(e -> refreshNotifications());
        viewPanel.add(viewBtn);
        leftPanel.add(viewPanel);

        JScrollPane leftScroll = new JScrollPane(leftPanel);
        leftScroll.setBorder(BorderFactory.createEmptyBorder());
        leftScroll.setPreferredSize(new Dimension(360, 0));

        // Notification list
        notifArea = new JTextArea();
        notifArea.setEditable(false);
        notifArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        notifArea.setBackground(new Color(240, 244, 255));
        notifArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane notifScroll = new JScrollPane(notifArea);
        notifScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 70, 127), 1), "All Notifications"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, notifScroll);
        splitPane.setDividerLocation(370);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        add(splitPane, BorderLayout.CENTER);

        refreshNotifications();
    }

    private void refreshNotifications() {
        List<String> notifications = customer.getNotifications();
        if (notifications.isEmpty()) {
            notifArea.setText("No notifications yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("Notifications for " + customer.getName() + ":\n\n");
        for (int i = 0; i < notifications.size(); i++) {
            sb.append(i + 1).append(". ").append(notifications.get(i)).append("\n\n");
        }
        notifArea.setText(sb.toString());
        notifArea.setCaretPosition(0);
    }

    private Account findAccount(String accountNumber) {
        if (accountNumber.isEmpty()) {
            showError("Account number cannot be empty.");
            return null;
        }
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
        label.setPreferredSize(new Dimension(130, 20));
        row.add(label);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setPreferredSize(new Dimension(160, 26));
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

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
