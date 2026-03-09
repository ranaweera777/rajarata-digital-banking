package src.main.java.com.rajarata.bank.models ;

public class Staff extends User {
    public Staff(String name, String email, String password) {
        super(name, email, password, "STAFF");
    }
}
