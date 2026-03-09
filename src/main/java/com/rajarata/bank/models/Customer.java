package src.main.java.com.rajarata.bank.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rajarata.bank.models.accounts.Account;
import src.main.java.com.rajarata.bank.models.loans.LoanApplication;

public class Customer extends User {
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String name, String email, String password) {
        super(name, email, password, "CUSTOMER");
    }

    public void addAccount(Account account) {
        if (account != null) {
            accounts.add(account);
        }
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }
}
