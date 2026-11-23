
package banking.app;

import banking.util.DatabaseConnection;
import banking.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AdminSetup {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("BANKING SYSTEM - ADMIN SETUP");

        System.out.print("Enter administrator email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Create administrator password: ");
        String password = scanner.nextLine();

        if (email.isEmpty() || password.length() < 8) {
            System.out.println(
                    "Email is required and password must have at least 8 characters."
            );
            return;
        }

        String passwordHash = PasswordUtil.hashPassword(password);

        String sql = "INSERT INTO users " +
                "(email, password_hash, role, customer_id) " +
                "VALUES (?, ?, 'ADMIN', NULL)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, passwordHash);

            statement.executeUpdate();

            System.out.println("Administrator account created successfully!");

        } catch (Exception e) {

            System.out.println(
                    "Could not create administrator: " + e.getMessage()
            );
        }
    }
}

