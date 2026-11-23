
package banking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Database connection details
    private static final String URL =
            "jdbc:mariadb://127.0.0.1:3306/banking_system";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    //Establishes and returns a connection to the MariaDB database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Tests the database connection and displays the available tables
    public static void main(String[] args) {
        try (Connection connection = getConnection();
             var statement = connection.createStatement();
             var result = statement.executeQuery(
                     "SELECT TABLE_NAME FROM information_schema.TABLES " +
                             "WHERE TABLE_SCHEMA = 'banking_system'")) {

            System.out.println("Tables in banking_system:");

            // Read and display each table name returned by the query
            while (result.next()) {
                System.out.println(result.getString("TABLE_NAME"));
            }

        } catch (SQLException e) {

            // Displays database errors if the connection or query fails
            e.printStackTrace();
        }
    }



}

