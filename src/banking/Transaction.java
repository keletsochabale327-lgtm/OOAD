
package banking;

import java.time.LocalDateTime;

public class Transaction {

    private String type;
    private String transactionID;
    private double amount;
    private LocalDateTime date;
    private double balanceAfter;
    private String description;

    public Transaction(String type, String transactionID,
                       double amount, double balanceAfter,
                       String description) {

        this.type = type;
        this.transactionID = transactionID;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction Type: " + type
                + "\nTransaction ID: " + transactionID
                + "\nAmount: BWP " + amount
                + "\nDate: " + date
                + "\nBalance After: BWP " + balanceAfter
                + "\nDescription: " + description;
    }
}
