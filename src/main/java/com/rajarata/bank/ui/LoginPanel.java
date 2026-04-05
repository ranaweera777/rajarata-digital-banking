package com.rajarata.bank.ui;

import com.rajarata.bank.exceptions.AuthenticationException;
import com.rajarata.bank.models.Customer;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(MainFrame mainFrame) {
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

        // Center form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        centerPanel.setBackground(new Color(245, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel loginTitle = new JLabel("Sign In", SwingConstants.CENTER);
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        loginTitle.setForeground(new Color(0, 70, 127));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 8, 20, 8);
        centerPanel.add(loginTitle, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        centerPanel.add(userLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(250, 36));
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        centerPanel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        centerPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(250, 36));
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1;
        centerPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        styleButton(loginButton, new Color(0, 70, 127));
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        centerPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("New Customer? Register here");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        registerButton.setForeground(new Color(0, 70, 127));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> mainFrame.showRegister());
        gbc.gridy = 4; gbc.insets = new Insets(4, 8, 8, 8);
        centerPanel.add(registerButton, gbc);

        // Also allow Enter key to trigger login
        passwordField.addActionListener(e -> handleLogin());

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(new Color(245, 248, 255));
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 230), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));
        formCard.add(centerPanel, BorderLayout.CENTER);
        wrapperPanel.add(formCard);
        add(wrapperPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(230, 235, 245));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel footerLabel = new JLabel("© Rajarata University Digital Banking System", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            com.rajarata.bank.models.User user = mainFrame.getAuthService().login(username, password);
            if (user instanceof Customer) {
                mainFrame.showDashboard((Customer) user);
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Only customer accounts are supported in the GUI.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (AuthenticationException e) {
            JOptionPane.showMessageDialog(this, "Login failed: " + e.getMessage(), "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }
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
