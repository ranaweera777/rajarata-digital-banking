package com.rajarata.bank.interfaces;

public interface Notifiable {
    void notifyUser(String message);
    void sendAlert(String message);
    void sendAlert(String alertType, String message);
    void sendNotification(String message);
}
