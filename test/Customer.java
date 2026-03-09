package test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends Person {
    private final List<Account> accounts;

    public Customer(String name, String email, String password) {
        super(name, email, password, "CUSTOMER");
        this.accounts = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void addAccount(Account account) {
        if (account != null) {
            accounts.add(account);
        }
    }
}