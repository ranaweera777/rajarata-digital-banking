package com.rajarata.bank.ui;

import com.rajarata.bank.models.Customer;
import com.rajarata.bank.services.BillPaymentService;
import com.rajarata.bank.services.LoanService;
import com.rajarata.bank.services.NotificationService;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;
    private final Customer customer;
    private final LoanService loanService;
    private final BillPaymentService billPaymentService;
    private final NotificationService notificationService;

    private final CardLayout featureCardLayout;
    private final JPanel featurePanel;

    private static final String ACCOUNT_CARD = "ACCOUNTS";
    private static final String LOAN_CARD = "LOANS";
    private static final String BILL_CARD = "BILLS";
    private static final String NOTIFICATION_CARD = "NOTIFICATIONS";
    private static final String STATEMENT_CARD = "STATEMENT";

    public DashboardPanel(MainFrame mainFrame, Customer customer,
                          LoanService loanService, BillPaymentService billPaymentService,
                          NotificationService notificationService) {
        this.mainFrame = mainFrame;
        this.customer = customer;
        this.loanService = loanService;
        this.billPaymentService = billPaymentService;
        this.notificationService = notificationService;

        featureCardLayout = new CardLayout();
        featurePanel = new JPanel(featureCardLayout);

        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0, 70, 127));
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getName() + "  |  Rajarata Digital Banking");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        topBar.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutBtn.setBackground(new Color(200, 50, 50));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> mainFrame.logout());
        topBar.add(logoutBtn, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // Left navigation
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(30, 50, 90));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        navPanel.setPreferredSize(new Dimension(200, 0));

        String[][] navItems = {
            {ACCOUNT_CARD, "Account Operations"},
            {LOAN_CARD, "Loan Processing"},
            {BILL_CARD, "Bill Payment"},
            {NOTIFICATION_CARD, "Notifications"},
            {STATEMENT_CARD, "Monthly Statement"}
        };

        for (String[] item : navItems) {
            JButton btn = createNavButton(item[1]);
            final String card = item[0];
            btn.addActionListener(e -> featureCardLayout.show(featurePanel, card));
            navPanel.add(btn);
            navPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        }

        add(navPanel, BorderLayout.WEST);

        // Feature panels
        featurePanel.add(new AccountPanel(customer), ACCOUNT_CARD);
        featurePanel.add(new LoanPanel(customer, loanService), LOAN_CARD);
        featurePanel.add(new BillPanel(customer, billPaymentService), BILL_CARD);
        featurePanel.add(new NotificationPanel(customer, notificationService), NOTIFICATION_CARD);
        featurePanel.add(new StatementPanel(customer), STATEMENT_CARD);

        add(featurePanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(30, 50, 90));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(200, 50));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(0, 70, 127));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(30, 50, 90));
            }
        });
        return btn;
    }
}
