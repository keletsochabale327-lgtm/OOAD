
package banking.dao;

import banking.model.User;
import banking.util.DatabaseConnection;
import banking.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Register login details for an already registered customer.
    public boolean registerCustomer(
            int customerID,
            String email,
            String password
    ) {

        String checkSQL = """
                SELECT customer_id
                FROM customers
                WHERE customer_id = ?
                  AND LOWER(email) = LOWER(?)
                """;

        String insertSQL = """
                INSERT INTO users
                (email, password_hash, role, customer_id)
                VALUES (?, ?, 'CUSTOMER', ?)
                """;

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return false;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // Confirm that the administrator registered this customer.
                try (PreparedStatement check =
                             connection.prepareStatement(checkSQL)) {

                    check.setInt(1, customerID);
                    check.setString(2, email.trim());

                    try (ResultSet result = check.executeQuery()) {

                        if (!result.next()) {
                            connection.rollback();
                            return false;
                        }
                    }
                }

                // Hash the password before storing it.
                String passwordHash =
                        PasswordUtil.hashPassword(password);

                try (PreparedStatement insert =
                             connection.prepareStatement(insertSQL)) {

                    insert.setString(1, email.trim());
                    insert.setString(2, passwordHash);
                    insert.setInt(3, customerID);

                    insert.executeUpdate();
                }

                connection.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Customer registration error: " + e.getMessage()
            );
            return false;
        }
    }

    // Authenticate an administrator or customer.
    public User authenticate(String email, String password) {

        String sql = """
                SELECT user_id, email, password_hash, role, customer_id
                FROM users
                WHERE LOWER(email) = LOWER(?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email.trim());

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String storedHash =
                            result.getString("password_hash");

                    if (PasswordUtil.verifyPassword(
                            password, storedHash)) {

                        int customerID =
                                result.getInt("customer_id");

                        Integer linkedCustomerID =
                                result.wasNull() ? null : customerID;

                        return new User(
                                result.getInt("user_id"),
                                result.getString("email"),
                                storedHash,
                                result.getString("role"),
                                linkedCustomerID
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Login error: " + e.getMessage()
            );
        }

        return null;
    }
}
