package src.main.java.com.rajarata.bank.exceptions
;

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String message) {
        super(message);
    }
}
