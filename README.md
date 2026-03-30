# Rajarata Digital Banking

Project scaffold organized by layers: models, services, security, utils, exceptions, interfaces, and data.

## Prerequisites

- **Java 17** or later
- **Apache Maven 3.8+**

## How to Run

```bash
# Compile and run in one step (interactive — reads from keyboard)
mvn compile exec:java

# Or build a JAR and run it
mvn package
java -jar target/rajarata-digital-banking-1.0-SNAPSHOT.jar
```

The interactive demo prompts you for input and walks through:

1. **Customer Registration** — enter user ID, username, password, email, phone, customer ID, address
2. **Login / Logout** — authenticate with username & password, view logged-in user
3. **Account Operations** — link savings & checking accounts, deposit, withdraw, transfer (amounts entered at runtime)
4. **Interest & Statement** — view interest rate, calculate & apply monthly interest, print monthly statement
5. **Loan Processing** — apply for a loan, approve it, make a payment, view status & remaining balance
6. **Bill Payment** — schedule bills with user-entered due days, list upcoming bills, pay from checking account
7. **Notifications** — transaction alerts, loan reminders, low-balance warnings, bill reminders; retrieve & print all
