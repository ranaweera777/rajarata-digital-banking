package src.main.java.com.rajarata.bank.security;

public class AccessControl {
    public boolean hasAccess(String role, String requiredRole) {
        return role != null && role.equalsIgnoreCase(requiredRole);
    }
}
