
package banking.controller;

import banking.dao.AccountDAO;
import banking.dao.CustomerDAO;
import banking.view.AccountRegistrationView;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class AccountRegistrationController {

    private final AccountRegistrationView view;
    private final AccountDAO accountDAO;
    private final CustomerDAO customerDAO;

    public AccountRegistrationController(AccountRegistrationView view) {
        this.view = view;
        this.accountDAO = new AccountDAO();
        this.customerDAO = new CustomerDAO();

        refreshCustomers();

        view.getOpenAccountButton().setOnAction(event -> openAccount());
    }

    // Refresh customer list
    public void refreshCustomers() {
        view.setCustomers(customerDAO.getAllCustomers());
    }

    // Open a new account
    private void openAccount() {
        try {
            String selectedCustomer = view.getCustomerBox().getValue();

            if (selectedCustomer == null || selectedCustomer.isBlank()) {
                view.showMessage(Alert.AlertType.WARNING,
                        "Please select a customer.");
                return;
            }

            int customerID = Integer.parseInt(
                    selectedCustomer.substring(0, selectedCustomer.indexOf(" - "))
            );

            String accountNumber = view.getAccountNumberField().getText().trim();
            String accountType = view.getAccountTypeBox().getValue();
            String depositText = view.getInitialDepositField().getText().trim();
            String branch = view.getBranchField().getText().trim();
            LocalDate openingDate = view.getOpeningDatePicker().getValue();

            if (accountNumber.isEmpty() || depositText.isEmpty()
                    || branch.isEmpty() || openingDate == null
                    || accountType == null) {

                view.showMessage(Alert.AlertType.WARNING,
                        "Please complete all account details.");
                return;
            }

            double balance = Double.parseDouble(depositText);

            if (balance < 0) {
                view.showMessage(Alert.AlertType.WARNING,
                        "The opening deposit cannot be negative.");
                return;
            }

            if ("INVESTMENT".equals(accountType) && balance < 500) {
                view.showMessage(Alert.AlertType.WARNING,
                        "An Investment Account requires at least BWP500.00.");
                return;
            }

            if ("CHEQUE".equals(accountType)
                    && !customerDAO.hasEmployerDetails(customerID)) {
                view.showMessage(Alert.AlertType.WARNING,
                        "A Cheque Account is only for working individuals. "
                                + "This customer has no employer details on record.");
                return;
            }

            double interestRate = 0.0;

            if ("SAVINGS".equals(accountType)) {
                interestRate = 0.0005;
            } else if ("INVESTMENT".equals(accountType)) {
                interestRate = 0.05;
            }

            boolean saved = accountDAO.addAccount(
                    accountNumber,
                    customerID,
                    accountType,
                    balance,
                    branch,
                    interestRate,
                    openingDate.toString()
            );

            if (saved) {
                view.showMessage(Alert.AlertType.INFORMATION,
                        "Account opened successfully!");

                view.clearForm();
                refreshCustomers();

            } else {
                view.showMessage(Alert.AlertType.ERROR,
                        "Account could not be opened. Check the customer and account details.");
            }

        } catch (NumberFormatException e) {
            view.showMessage(Alert.AlertType.ERROR,
                    "Enter a valid deposit amount.");

        } catch (Exception e) {
            view.showMessage(Alert.AlertType.ERROR,
                    "Error: " + e.getMessage());
        }
    }
}
