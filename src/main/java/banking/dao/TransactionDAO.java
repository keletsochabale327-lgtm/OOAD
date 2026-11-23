
package banking.dao;

import banking.util.DatabaseConnection;
import banking.model.TransactionRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Record a new transaction.
    public boolean addTransaction(
            int accountID,
            String transactionType,
            double amount,
            double balanceAfter,
            String description) {

        String sql = "INSERT INTO transactions " +
                "(account_id, transaction_type, amount, balance_after, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accountID);
            statement.setString(2, transactionType);
            statement.setDouble(3, amount);
            statement.setDouble(4, balanceAfter);
            statement.setString(5, description);

            statement.executeUpdate();

            System.out.println("Transaction recorded successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("Error recording transaction: " + e.getMessage());
            return false;
        }
    }

    // Retrieve transaction history for an account.
    public List<TransactionRecord> getTransactionsByAccount(int accountID) {

        List<TransactionRecord> list = new ArrayList<>();

        String sql = "SELECT transaction_id, transaction_type, amount, " +
                "transaction_date, balance_after, description " +
                "FROM transactions WHERE account_id = ? " +
                "ORDER BY transaction_date DESC, transaction_id DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accountID);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    list.add(new TransactionRecord(
                            String.valueOf(result.getInt("transaction_id")),
                            result.getString("transaction_type"),
                            result.getDouble("amount"),
                            String.valueOf(result.getTimestamp("transaction_date")),
                            result.getDouble("balance_after"),
                            result.getString("description")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
        }

        return list;
    }

    // Retrieve transaction history after verifying account ownership.
    public List<TransactionRecord> getTransactionsByCustomer(
            int customerID,
            int accountID) {

        List<TransactionRecord> list = new ArrayList<>();

        String sql = "SELECT t.transaction_id, t.transaction_type, " +
                "t.amount, t.transaction_date, t.balance_after, t.description " +
                "FROM transactions t " +
                "INNER JOIN accounts a ON t.account_id = a.account_id " +
                "WHERE a.customer_id = ? AND a.account_id = ? " +
                "ORDER BY t.transaction_date DESC, t.transaction_id DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerID);
            statement.setInt(2, accountID);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    list.add(new TransactionRecord(
                            String.valueOf(result.getInt("transaction_id")),
                            result.getString("transaction_type"),
                            result.getDouble("amount"),
                            String.valueOf(result.getTimestamp("transaction_date")),
                            result.getDouble("balance_after"),
                            result.getString("description")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving customer transactions: " + e.getMessage()
            );
        }

        return list;
    }

    // Display transaction history in the console.
    public void displayTransactionHistory(int accountID) {

        for (TransactionRecord transaction : getTransactionsByAccount(accountID)) {

            System.out.println(
                    "Transaction ID: " + transaction.getTransactionID() +
                            ", Type: " + transaction.getType() +
                            ", Amount: " + transaction.getAmount() +
                            ", Date: " + transaction.getDate() +
                            ", Balance After: " + transaction.getBalanceAfter() +
                            ", Description: " + transaction.getDescription()
            );
        }
    }
}
