# Rajarata Digital Banking — TODO List

## ✅ Completed

- [x] Remove UTF-8 BOM markers from 19 Java files and pom.xml (prevented compilation)
- [x] Fix `Transactable` interface: `deposit`/`withdraw` now return `boolean` (matching usage)
- [x] Add missing `return` statements in `Account.deposit()` and `Account.withdraw()`
- [x] Fix `CheckingAccount.withdraw()` return type to match interface
- [x] Fix `FixedDepositAccount.withdraw()` return type to match interface
- [x] Fix `AuthenticationService.login()`: use `username` (not `password`) as key for `failedAttempts`
- [x] Fix `AuthenticationService.login()`: compare password hash instead of checking password strength
- [x] Fix `PasswordValidator.hashPassword()`: replace insecure `hashCode()` with SHA-256
- [x] Fix `Loan`: initialize `nextDueDate` to prevent `NullPointerException`
- [x] Fix `Loan`: rename `termMonts` → `termMonths`, `principleAmount` → `principalAmount`
- [x] Fix `Loan.makePayment()`: correct interest calculation logic
- [x] Remove unrelated `StudentAccount` fields from `CheckingAccount`
- [x] Remove commented-out code blocks and stale TODO comments
- [x] Fix formatting issues in `User.java`
- [x] Remove unused imports in `BillPaymentService`
- [x] Make the application runnable — expand `Main.java` with a full demo (accounts, deposits, withdrawals, transfers, loans, bills, notifications)
- [x] Add `exec-maven-plugin` and `maven-jar-plugin` to `pom.xml` — run with `mvn exec:java`

## 🔲 Remaining — Critical

- [ ] Implement `DataStore` class (`src/main/java/com/rajarata/bank/data/DataStore.java`) — currently empty
- [ ] Implement `FileHandler` class (`src/main/java/com/rajarata/bank/data/FileHandler.java`) — currently empty
- [ ] Implement `AccountService` class (`src/main/java/com/rajarata/bank/services/AccountService.java`) — currently empty
- [ ] Implement `TransactionService` class (`src/main/java/com/rajarata/bank/services/TransactionService.java`) — currently empty

## 🔲 Remaining — High

- [ ] Make `DepositTransaction`, `WithdrawalTransaction`, and `TransferTransaction` concrete (remove `abstract`) — they have full implementations but cannot be instantiated
- [ ] Add JUnit 5 and Mockito dependencies to `pom.xml` and create proper test suite in `src/test/java`
- [ ] Remove or restructure legacy test files in root `test/` directory (they are duplicate class definitions, not actual tests)
- [ ] Add salt to SHA-256 password hashing in `PasswordValidator` (or switch to bcrypt/Argon2)

## 🔲 Remaining — Medium

- [ ] Fix `FixedDepositAccount` constructor: `depositAmount` parameter is unused; `earlyWithdrawalPenaltyRate` parameter is ignored (hardcoded to `2.0`)
- [ ] Extract magic numbers to named constants:
  - `Bill.java` line 44 — `0.02` late fee rate
  - `LoanService.java` line 56 — `0.05` penalty rate
  - `FixedDepositAccount.java` line 20 — `2.0` early withdrawal penalty
  - `InputValidator.java` line 27 — `1_000_000_000` max amount
- [ ] Replace `System.out.println` calls with a proper logging framework (SLF4J / Log4j) across 30+ occurrences
- [ ] Add thread-safety / synchronization for shared collections in `AuthenticationService` and `LoanService`

## 🔲 Remaining — Low

- [ ] Replace wildcard import `java.util.*` in `AccessControl.java` with specific imports
- [ ] Clean up inconsistent indentation and mixed formatting across multiple files
- [ ] Remove unnecessary `DateUtils` wrapper (just delegates to `LocalDateTime.now()`)
- [ ] Improve `CurrencyConverter` — currently only supports static rate multiplication
- [ ] Add Javadoc comments to public classes and methods
- [ ] Populate resource data files (`customers.txt`, `accounts.txt`, `transactions.txt`, `loans.txt`) or remove if unused
