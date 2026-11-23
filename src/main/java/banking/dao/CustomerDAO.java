
package banking.dao;

import banking.util.DatabaseConnection;
import banking.model.IndividualCustomer;
import banking.model.CompanyCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Register an individual customer
    public String addIndividualCustomer(IndividualCustomer customer) {

        String customerSQL = "INSERT INTO customers " +
                "(first_name, surname, email, address, customer_type, phone) " +
                "VALUES (?, ?, ?, ?, 'INDIVIDUAL', ?)";

        String individualSQL = "INSERT INTO individual_customers " +
                "(customer_id, id_number, is_employed, company_name, company_address, date_of_birth) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                int customerID;

                String employerName = customer.getEmployerName();
                String employerAddress = customer.getEmployerAddress();

                boolean employed = employerName != null
                        && !employerName.isBlank()
                        && employerAddress != null
                        && !employerAddress.isBlank();

                try (PreparedStatement statement = connection.prepareStatement(
                        customerSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    statement.setString(1, customer.getFirstName());
                    statement.setString(2, customer.getSurname());
                    statement.setString(3, customer.getEmail());
                    statement.setString(4, customer.getAddress());
                    statement.setString(5, String.valueOf(customer.getPhone()));

                    statement.executeUpdate();

                    try (ResultSet keys = statement.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("Customer ID was not generated.");
                        }

                        customerID = keys.getInt(1);
                    }
                }

                try (PreparedStatement statement =
                             connection.prepareStatement(individualSQL)) {

                    statement.setInt(1, customerID);
                    statement.setString(2, String.valueOf(customer.getIdNumber()));
                    statement.setBoolean(3, employed);
                    statement.setString(4, employerName);
                    statement.setString(5, employerAddress);

                    if (customer.getDateOfBirth() == null ||
                            customer.getDateOfBirth().isBlank()) {
                        statement.setNull(6, Types.DATE);
                    } else {
                        statement.setDate(6, Date.valueOf(customer.getDateOfBirth()));
                    }

                    statement.executeUpdate();
                }

                connection.commit();

                return String.valueOf(customerID);

            } catch (SQLException | IllegalArgumentException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error registering individual customer: "
                    + e.getMessage());
            return null;
        }
    }


    // Register a company customer
    public String addCompanyCustomer(CompanyCustomer customer,
                                     String firstName,
                                     String surname) {

        String customerSQL = "INSERT INTO customers " +
                "(first_name, surname, email, address, customer_type, phone) " +
                "VALUES (?, ?, ?, ?, 'COMPANY', ?)";

        String companySQL = "INSERT INTO company_customers " +
                "(customer_id, company_name, company_address, registration_number, contact_person) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                int customerID;

                try (PreparedStatement statement = connection.prepareStatement(
                        customerSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    statement.setString(1, firstName);
                    statement.setString(2, surname);
                    statement.setString(3, customer.getEmail());
                    statement.setString(4, customer.getAddress());
                    statement.setString(5, String.valueOf(customer.getPhone()));

                    statement.executeUpdate();

                    try (ResultSet keys = statement.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("Customer ID was not generated.");
                        }

                        customerID = keys.getInt(1);
                    }
                }

                try (PreparedStatement statement =
                             connection.prepareStatement(companySQL)) {

                    statement.setInt(1, customerID);
                    statement.setString(2, customer.getCompanyName());
                    statement.setString(3, customer.getBusinessAddress());
                    statement.setString(4, customer.getRegistrationNumber());
                    statement.setString(5, customer.getContactPerson());

                    statement.executeUpdate();
                }

                connection.commit();

                return String.valueOf(customerID);

            } catch (SQLException | IllegalArgumentException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error registering company customer: "
                    + e.getMessage());
            return null;
        }
    }


    // Retrieve all registered customers
    public List<String> getAllCustomers() {

        List<String> customers = new ArrayList<>();

        String sql = "SELECT customer_id, first_name, surname, customer_type " +
                "FROM customers ORDER BY customer_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                String customer = result.getInt("customer_id")
                        + " - "
                        + result.getString("first_name")
                        + " "
                        + result.getString("surname")
                        + " ("
                        + result.getString("customer_type")
                        + ")";

                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }

        return customers;
    }


    // Check whether an individual customer has employer details
    public boolean hasEmployerDetails(int customerID) {

        String sql = "SELECT company_name, company_address " +
                "FROM individual_customers WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerID);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String name = result.getString("company_name");
                    String address = result.getString("company_address");

                    return name != null && !name.isBlank()
                            && address != null && !address.isBlank();
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking employer details: "
                    + e.getMessage());
        }

        return false;
    }


    // Retrieve employer details
    public String[] getEmployerDetails(int customerID) {

        String sql = "SELECT company_name, company_address " +
                "FROM individual_customers WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerID);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    return new String[]{
                            result.getString("company_name"),
                            result.getString("company_address")
                    };
                }
            }

        } catch (SQLException e) {
            System.out.println("Error reading employer details: "
                    + e.getMessage());
        }

        return null;
    }
}
