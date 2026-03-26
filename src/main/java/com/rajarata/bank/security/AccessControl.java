
package com.rajarata.bank.security;

import com.rajarata.bank.models.User;
import java.util.*;

public class AccessControl {
    private Map<String, Set<String>> rolePermissions;

    public AccessControl() {
        rolePermissions = new HashMap<>();
        initializePermissions();
    }

    private void initializePermissions() {
        // Customer permissions
        Set<String> customerPerms = new HashSet<>(Arrays.asList(
            "VIEW_OWN_ACCOUNTS", "DEPOSIT", "WITHDRAW", "TRANSFER",
            "VIEW_TRANSACTIONS", "APPLY_LOAN", "PAY_BILLS", "VIEW_STATEMENTS"
        ));
        rolePermissions.put("CUSTOMER", customerPerms);

        // Staff permissions (includes customer permissions)
        Set<String> staffPerms = new HashSet<>(customerPerms);
        staffPerms.addAll(Arrays.asList(
            "APPROVE_LOAN", "REJECT_LOAN", "VIEW_ALL_ACCOUNTS",
            "MONITOR_ACTIVITY", "FLAG_SUSPICIOUS", "VIEW_CUSTOMER_INFO"
        ));
        rolePermissions.put("STAFF", staffPerms);

        // Admin permissions (all permissions)
        Set<String> adminPerms = new HashSet<>(staffPerms);
        adminPerms.addAll(Arrays.asList(
            "MANAGE_USERS", "VIEW_AUDIT_LOGS", "GENERATE_REPORTS",
            "SYSTEM_CONFIG", "DEACTIVATE_ACCOUNT", "OVERRIDE_LIMITS"
        ));
        rolePermissions.put("ADMIN", adminPerms);
    }

    public boolean hasPermission(User user, String permission) {
        Set<String> permissions = rolePermissions.get(user.getRole());
        return permissions != null && permissions.contains(permission);
    }

    public void checkPermission(User user, String permission) throws SecurityException {
        if (!hasPermission(user, permission)) {
            throw new SecurityException(
                "Access denied. User " + user.getName() + 
                " does not have permission: " + permission
            );
        }
    }

    public Set<String> getUserPermissions(User user) {
        return new HashSet<>(rolePermissions.getOrDefault(user.getRole(), new HashSet<>()));
    }
}