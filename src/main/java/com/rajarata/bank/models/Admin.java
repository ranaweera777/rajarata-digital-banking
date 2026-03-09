package src.main.java.com.rajarata.bank.models;

public class Admin extends User {
    public Admin(String name, String email, String password) {
        super(name, email, password, "ADMIN");
    }
}
