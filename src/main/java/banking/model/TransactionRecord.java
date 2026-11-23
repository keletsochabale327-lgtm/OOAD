package banking.model;

// Plain data holder for one row of the transactions table (used by the history table).
public class TransactionRecord {

    private final String transactionID;
    private final String type;
    private final double amount;
    private final String date;
    private final double balanceAfter;
    private final String description;

    public TransactionRecord(String transactionID, String type, double amount,
                             String date, double balanceAfter, String description) {
        this.transactionID = transactionID;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }

    public String getTransactionID() { return transactionID; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getDescription() { return description; }
}
