package com.rajarata.bank.ui;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.services.AuthenticationService;
import com.rajarata.bank.services.BillPaymentService;
import com.rajarata.bank.services.LoanService;
import com.rajarata.bank.services.NotificationService;
import com.rajarata.bank.services.TransactionService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final String LOGIN_PANEL = "LOGIN";
    private static final String REGISTER_PANEL = "REGISTER";
    private static final String DASHBOARD_PANEL = "DASHBOARD";

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private final AuthenticationService authService;
    private final LoanService loanService;
    private final TransactionService transactionService;
    private final BillPaymentService billPaymentService;
    private final NotificationService notificationService;

    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    private DashboardPanel dashboardPanel;

    public MainFrame() {
        authService = new AuthenticationService();
        loanService = new LoanService();
        transactionService = new TransactionService();
        billPaymentService = new BillPaymentService(transactionService);
        notificationService = new NotificationService();

        setTitle("Rajarata Digital Banking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        buildMenuBar();
        buildPanels();

        add(mainPanel);
        cardLayout.show(mainPanel, LOGIN_PANEL);
    }

    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> logout());
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Rajarata Digital Banking\nVersion 1.0\n© Rajarata University",
                "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void buildPanels() {
        loginPanel = new LoginPanel(this);
        registrationPanel = new RegistrationPanel(this);

        mainPanel.add(loginPanel, LOGIN_PANEL);
        mainPanel.add(registrationPanel, REGISTER_PANEL);
    }

    public void showLogin() {
        cardLayout.show(mainPanel, LOGIN_PANEL);
    }

    public void showRegister() {
        cardLayout.show(mainPanel, REGISTER_PANEL);
    }

    public void showDashboard(Customer customer) {
        if (dashboardPanel != null) {
            mainPanel.remove(dashboardPanel);
        }
        dashboardPanel = new DashboardPanel(this, customer, loanService, billPaymentService, notificationService);
        mainPanel.add(dashboardPanel, DASHBOARD_PANEL);
        cardLayout.show(mainPanel, DASHBOARD_PANEL);
    }

    public void logout() {
        authService.logout();
        cardLayout.show(mainPanel, LOGIN_PANEL);
    }

    public AuthenticationService getAuthService() {
        return authService;
    }
}
