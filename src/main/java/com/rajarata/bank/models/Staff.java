package com.rajarata.bank.models;

import java.time.LocalDateTime;

public class Staff extends User {
    private String staffId;
    private String department;
    private boolean canApproveLoan;
    private boolean canMonitorActivity;

    public Staff(String staffId, String name, String email, String password,
                 String department, LocalDateTime createdAt, boolean isActive) {
        super(name, email, password, "STAFF", staffId, createdAt, isActive);
        this.staffId = staffId;
        this.department = department;
        this.canApproveLoan = true;
        this.canMonitorActivity = true;
    }

    @Override
    public String getRole() {
        return "STAFF";
    }

    public boolean canApproveLoan(String loanId) {
        if (canApproveLoan) {
            System.out.println("Staff " + getName() + " is approving loan application: " + loanId);
            return true;
        }
        return false;
    }

    public void flagSuspiciousActivity(String accountId, String reason) {
        System.out.println("SUSPICIOUS ACTIVITY FLAGGED by " + getName() +
                           " - Account: " + accountId + " Reason: " + reason);
    }

    // Getters and Setters
    public String getStaffId() { return staffId; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public boolean isCanApproveLoan() { return canApproveLoan; }
    public void setCanApproveLoan(boolean canApproveLoan) { this.canApproveLoan = canApproveLoan; }
    public boolean isCanMonitorActivity() { return canMonitorActivity; }
    public void setCanMonitorActivity(boolean canMonitorActivity) { this.canMonitorActivity = canMonitorActivity; }
}
