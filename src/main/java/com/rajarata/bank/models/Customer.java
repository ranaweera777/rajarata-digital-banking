package com.rajarata.bank.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.rajarata.bank.models.accounts.Account;
import com.rajarata.bank.interfaces.Notifiable;

// Inheritance: Customer extends User
public class Customer extends User implements Notifiable {
    private String customerId;
    private String phone;
    private String address;
    private List<Account> accounts;  // Composition: Customer HAS accounts
    private List<String> notifications;

    public Customer(String userId, String username, String password,
                    String email, String phone, String customerId, String address) {
        super(username, email, password, "CUSTOMER", userId, LocalDateTime.now(), true);
        this.phone = phone;
        this.customerId = customerId;
        this.address = address;
        this.accounts = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    // Polymorphism: Override abstract method
    @Override
    public String getRole() {
        return "CUSTOMER";
    }

    // Implement Notifiable interface
    @Override
    public void notifyUser(String message) {
        notifications.add(message);
        System.out.println("Notification to " + getName() + ": " + message);
    }

    @Override
    public void sendNotification(String message) {
        notifyUser(message);
    }

    @Override
    public void sendAlert(String message) {
        sendAlert("ALERT", message);
    }

    @Override
    public void sendAlert(String alertType, String message) {
        String alert = "[" + alertType + "] " + message;
        notifications.add(alert);
        System.out.println("ALERT to " + getName() + ": " + alert);
    }

    // Account management
    public void addAccount(Account account) {
        if (account != null) accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts); // Return copy for encapsulation
    }

    public String getCustomerId() { return customerId; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<String> getNotifications() { return new ArrayList<>(notifications); }
}
