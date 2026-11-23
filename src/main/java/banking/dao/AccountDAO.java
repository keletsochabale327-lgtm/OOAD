
package banking.dao;

import banking.util.DatabaseConnection;
import banking.model.AccountRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // Register a new account for an existing customer.
    // A customer can have multiple accounts.
    public boolean addAccount(
            String accountNumber,
            int customerID,
            String accountType,
            double balance,
            String branch,
            double interestRate,
            String openDate) {

        String sql = "INSERT INTO accounts " +
                "(account_number, customer_id, account_type, balance, " +
                "branch, interest_rate, open_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);
            statement.setInt(2, customerID);
            statement.setString(3, accountType);
            statement.setDouble(4, balance);
            statement.setString(5, branch);
            statement.setDouble(6, interestRate);
            statement.setDate(7, java.sql.Date.valueOf(openDate));

            statement.executeUpdate();
            return true;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error adding account: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all accounts.
    public List<AccountRecord> getAllAccountRecords() {

        List<AccountRecord> accounts = new ArrayList<>();

        String sql = "SELECT account_id, customer_id, account_number, " +
                "account_type, balance, branch FROM accounts " +
                "ORDER BY account_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                accounts.add(new AccountRecord(
                        result.getInt("account_id"),
                        result.getInt("customer_id"),
                        result.getString("account_number"),
                        result.getString("account_type"),
                        result.getDouble("balance"),
                        result.getString("branch")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        }

        return accounts;
    }

    // Retrieve account records belonging to one customer only.
    public List<AccountRecord> getAccountRecordsByCustomerID(int customerID) {

        List<AccountRecord> accounts = new ArrayList<>();

        String sql = "SELECT account_id, customer_id, account_number, " +
                "account_type, balance, branch FROM accounts " +
                "WHERE customer_id = ? ORDER BY account_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerID);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    accounts.add(new AccountRecord(
                            result.getInt("account_id"),
                            result.getInt("customer_id"),
                            result.getString("account_number"),
                            result.getString("account_type"),
                            result.getDouble("balance"),
                            result.getString("branch")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving customer accounts: " + e.getMessage()
            );
        }

        return accounts;
    }

    // Administrator transaction method.
    public boolean processTransaction(
            int accountID,
            String transactionType,
            double amount,
            String description) {

        return processTransactionInternal(
                null, accountID, transactionType, amount, description
        );
    }

    // Customer transaction method with ownership verification.
    public boolean processCustomerTransaction(
            int customerID,
            int accountID,
            String transactionType,
            double amount,
            String description) {

        return processTransactionInternal(
                customerID, accountID, transactionType, amount, description
        );
    }

    // Shared transaction processing logic.
    private boolean processTransactionInternal(
            Integer customerID,
            int accountID,
            String transactionType,
            double amount,
            String description) {

        if (amount <= 0 || !Double.isFinite(amount)) {
            System.out.println("Transaction amount must be positive.");
            return false;
        }

        if (transactionType == null) {
            System.out.println("Invalid transaction type.");
            return false;
        }

        String type = transactionType.toUpperCase();

        if (!type.equals("DEPOSIT") && !type.equals("WITHDRAWAL")) {
            System.out.println("Invalid transaction type.");
            return false;
        }

        String selectSQL =
                "SELECT account_type, balance FROM accounts " +
                        "WHERE account_id = ?" +
                        (customerID != null ? " AND customer_id = ?" : "") +
                        " FOR UPDATE";

        String updateSQL =
                "UPDATE accounts SET balance = ? WHERE account_id = ?" +
                        (customerID != null ? " AND customer_id = ?" : "");

        String insertSQL =
                "INSERT INTO transactions " +
                        "(account_id, transaction_type, amount, balance_after, description) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                String accountType;
                double currentBalance;

                // Lock the account and verify ownership.
                try (PreparedStatement select =
                             connection.prepareStatement(selectSQL)) {

                    select.setInt(1, accountID);

                    if (customerID != null) {
                        select.setInt(2, customerID);
                    }

                    try (ResultSet result = select.executeQuery()) {

                        if (!result.next()) {
                            throw new SQLException(
                                    "Account not found or access denied."
                            );
                        }

                        accountType = result.getString("account_type");
                        currentBalance = result.getDouble("balance");
                    }
                }

                // Savings accounts do not allow withdrawals.
                if (type.equals("WITHDRAWAL")
                        && accountType.equalsIgnoreCase("SAVINGS")) {

                    throw new SQLException(
                            "Withdrawals are not permitted from Savings Accounts."
                    );
                }

                // Prevent overdrawing.
                if (type.equals("WITHDRAWAL")
                        && amount > currentBalance) {

                    throw new SQLException("Insufficient account balance.");
                }

                double newBalance;

                if (type.equals("DEPOSIT")) {
                    newBalance = currentBalance + amount;
                } else {
                    newBalance = currentBalance - amount;
                }

                // Update account balance.
                try (PreparedStatement update =
                             connection.prepareStatement(updateSQL)) {

                    update.setDouble(1, newBalance);
                    update.setInt(2, accountID);

                    if (customerID != null) {
                        update.setInt(3, customerID);
                    }

                    if (update.executeUpdate() != 1) {
                        throw new SQLException(
                                "Account balance was not updated."
                        );
                    }
                }

                // Save transaction history.
                try (PreparedStatement insert =
                             connection.prepareStatement(insertSQL)) {

                    insert.setInt(1, accountID);
                    insert.setString(2, type);
                    insert.setDouble(3, amount);
                    insert.setDouble(4, newBalance);
                    insert.setString(5, description);

                    insert.executeUpdate();
                }

                connection.commit();

                System.out.println("Transaction completed successfully.");
                return true;

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Transaction failed: " + e.getMessage()
                );

                return false;

            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {

            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all accounts belonging to one customer as formatted strings.
    public List<String> getAccountsByCustomerID(int customerID) {

        List<String> accounts = new ArrayList<>();

        String sql = "SELECT account_id, account_number, account_type, " +
                "balance, branch FROM accounts " +
                "WHERE customer_id = ? ORDER BY account_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerID);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    accounts.add(
                            "Account ID: " + result.getInt("account_id") +
                                    " | Number: " + result.getString("account_number") +
                                    " | Type: " + result.getString("account_type") +
                                    " | Balance: BWP " + result.getBigDecimal("balance") +
                                    " | Branch: " + result.getString("branch")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving customer accounts: " + e.getMessage()
            );
        }

        return accounts;
    }

    // Retrieve all accounts as formatted strings.
    public List<String> getAllAccounts() {

        List<String> accounts = new ArrayList<>();

        String sql = "SELECT account_number, customer_id, account_type, " +
                "balance, branch FROM accounts ORDER BY account_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                accounts.add(
                        "Account Number: " + result.getString("account_number") +
                                " | Customer ID: " + result.getInt("customer_id") +
                                " | Type: " + result.getString("account_type") +
                                " | Balance: BWP " + result.getBigDecimal("balance") +
                                " | Branch: " + result.getString("branch")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        }

        return accounts;
    }

    // Display all accounts in the console.
    public void displayAccounts() {

        for (String account : getAllAccounts()) {
            System.out.println(account);
        }
    }
}
