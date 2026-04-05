package com.rajarata.bank.ui;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.security.PasswordValidator;

import javax.swing.*;
import java.awt.*;

public class RegistrationPanel extends JPanel {

    private final MainFrame mainFrame;
    private JTextField userIdField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField customerIdField;
    private JTextField addressField;

    public RegistrationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 70, 127));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Rajarata Digital Banking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel formTitle = new JLabel("Create New Account", SwingConstants.CENTER);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setForeground(new Color(0, 70, 127));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 8, 18, 8);
        formPanel.add(formTitle, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(7, 8, 7, 8);

        String[] labels = {"User ID:", "Username:", "Password:", "Email:", "Phone:", "Customer ID:", "Address:"};
        int row = 1;
        for (String lbl : labels) {
            JLabel label = new JLabel(lbl);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
            formPanel.add(label, gbc);
            row++;
        }

        Dimension fieldSize = new Dimension(250, 32);

        userIdField = makeField(fieldSize);
        usernameField = makeField(fieldSize);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordField.setPreferredSize(fieldSize);
        emailField = makeField(fieldSize);
        phoneField = makeField(fieldSize);
        customerIdField = makeField(fieldSize);
        addressField = makeField(fieldSize);

        JComponent[] fields = {userIdField, usernameField, passwordField, emailField, phoneField, customerIdField, addressField};
        for (int i = 0; i < fields.length; i++) {
            gbc.gridx = 1; gbc.gridy = i + 1; gbc.weightx = 1;
            formPanel.add(fields[i], gbc);
        }

        JButton registerButton = new JButton("Register");
        styleButton(registerButton, new Color(0, 70, 127));
        registerButton.addActionListener(e -> handleRegister());
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(18, 8, 8, 8);
        formPanel.add(registerButton, gbc);

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        backButton.setForeground(new Color(0, 70, 127));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> mainFrame.showLogin());
        gbc.gridy = row + 1; gbc.insets = new Insets(4, 8, 8, 8);
        formPanel.add(backButton, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    private JTextField makeField(Dimension size) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setPreferredSize(size);
        return field;
    }

    private void handleRegister() {
        String userId = userIdField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String customerId = customerIdField.getText().trim();
        String address = addressField.getText().trim();

        if (userId.isEmpty() || username.isEmpty() || password.isEmpty() ||
                email.isEmpty() || phone.isEmpty() || customerId.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!PasswordValidator.isStrongPassword(password)) {
            JOptionPane.showMessageDialog(this,
                    PasswordValidator.getPasswordRequirements(),
                    "Weak Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Customer customer = new Customer(userId, username, password, email, phone, customerId, address);
        boolean success = mainFrame.getAuthService().register(customer, password);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful! Please log in.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            mainFrame.showLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration failed. Username or email may already exist, or invalid input.",
                    "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        userIdField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        emailField.setText("");
        phoneField.setText("");
        customerIdField.setText("");
        addressField.setText("");
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
