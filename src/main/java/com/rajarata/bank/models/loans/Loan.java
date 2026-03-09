package src.main.java.com.rajarata.bank.models.loans;

public class Loan {
    private String id;
    private double amount;

    public Loan(String id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() { return id; }
    public double getAmount() { return amount; }
}
