package banking.model;

// Plain data holder for one row of the accounts table.
public class AccountRecord {

    private final int accountID;
    private final int customerID;
    private final String accountNumber;
    private final String accountType;
    private final double balance;
    private final String branch;

    public AccountRecord(int accountID, int customerID, String accountNumber,
                         String accountType, double balance, String branch) {
        this.accountID = accountID;
        this.customerID = customerID;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.branch = branch;
    }

    public int getAccountID() { return accountID; }
    public int getCustomerID() { return customerID; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public String getBranch() { return branch; }

    // Shown in the account drop-downs
    @Override
    public String toString() {
        return accountNumber + " | " + accountType
                + " | BWP " + String.format("%.2f", balance)
                + " | Customer " + customerID;
    }
}
