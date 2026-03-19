package com.rajarata.bank.models;

import java.time.LocalDateTime;

public class Admin extends User {
    private final String adminId;
    private String accessLevel;

    public Admin(String id, String name, String email, String password,
                 LocalDateTime createdAt, boolean isActive) {
        super(name, email, password, "ADMIN", id, createdAt, isActive);
        this.adminId = id;
        this.accessLevel = "FULL";
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    public void manageUserAccount(User user, String action) {
        if (user == null || action == null) {
            return;
        }

        switch (action.toUpperCase()) {
            case "ACTIVATE":
                user.setActive(true);
                System.out.println("Admin activated user: " + user.getName());
                break;
            case "DEACTIVATE":
                user.setActive(false);
                System.out.println("Admin deactivated user: " + user.getName());
                break;
            default:
                System.out.println("Unknown action: " + action);
        }
    }

    public void generateComplianceReport() {
        System.out.println("Generating compliance report...");
    }

    public void viewAuditLogs() {
        System.out.println("Viewing audit logs...");
    }

    public String getAdminId() {
        return adminId;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        if (accessLevel != null && !accessLevel.isBlank()) {
            this.accessLevel = accessLevel;
        }
    }
}
