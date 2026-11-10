
package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account {

    private String accountNumber;
    private double balance;
    private String branch;
    private String openDate;
    private String status;

    private List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber, String branch,
                   double openingBalance) {

        if (openingBalance < 0) {
            throw new IllegalArgumentException(
                    "Opening balance cannot be negative.");
        }

        this.accountNumber = accountNumber;
        this.branch = branch;
        this.balance = openingBalance;
        this.openDate = java.time.LocalDate.now().toString();
        this.status = "ACTIVE";
    }

    // Deposit money into the account
    public void deposit(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Deposit must be greater than zero.");
        }

        balance += amount;

        recordTransaction("DEPOSIT", amount);
    }

    public void deposit(double amount, String description) {
    if (amount <= 0) {
        throw new IllegalArgumentException(
                "Deposit must be greater than zero.");
    }

    balance += amount;

    Transaction transaction = new Transaction(
            "DEPOSIT",
            UUID.randomUUID().toString(),
            amount,
            balance,
            description
    );

    transactions.add(transaction);
}

   public abstract void withdraw(double amount);

    // Withdrawal logic for eligible account types
    protected void withdrawFromAccount(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal must be greater than zero.");
        }

        if (amount > balance) {
            throw new IllegalArgumentException(
                    "Insufficient funds.");
        }

        balance -= amount;

        recordTransaction("WITHDRAWAL", amount);
    }

    // Record every transaction
    protected void recordTransaction(String type, double amount) {

        Transaction transaction = new Transaction(
                type,
                UUID.randomUUID().toString(),
                amount,
                balance,
                "Account transaction"
        );

        transactions.add(transaction);
    }

    // Add interest to account balance
    protected void addInterest(double amount) {

        if (amount > 0) {
            balance += amount;
            recordTransaction("INTEREST", amount);
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getBranch() {
        return branch;
    }

    public String getOpenDate() {
        return openDate;
    }

    public String getStatus() {
        return status;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract String getAccountType();
}
