package src.main.java.com.rajarata.bank.models.bills;

public class Bill {
    private String billId;
    private double amount;

    public Bill(String billId, double amount) {
        this.billId = billId;
        this.amount = amount;
    }

    public String getBillId() { return billId; }
    public double getAmount() { return amount; }
}
