
package banking.controller;

import banking.dao.AccountDAO;
import banking.dao.TransactionDAO;
import banking.model.AccountRecord;
import banking.model.TransactionRecord;
import banking.model.User;
import banking.view.CustomerDashboardView;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import java.util.List;

public class CustomerDashboardController {

    // Stores the dashboard, logged-in user and database access objects
    private final CustomerDashboardView view;
    private final User user;

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    // Initializes the controller and loads the customer's accounts
    public CustomerDashboardController(
            CustomerDashboardView view,
            User user) {

        this.view = view;
        this.user = user;

        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();

        // Make sure this user has a linked customer record.
        if (user.getCustomerID() == null) {
            showError("This customer account is not linked to a customer record.");
            return;
        }

        configureAccountSelector();
        connectEvents();
        loadAccounts();
    }

    // Display account numbers and types in the account selector.
    private void configureAccountSelector() {

        StringConverter<AccountRecord> converter =
                new StringConverter<>() {

                    @Override
                    public String toString(AccountRecord account) {

                        if (account == null) {
                            return "";
                        }

                        return account.getAccountNumber()
                                + " - "
                                + account.getAccountType();
                    }

                    @Override
                    public AccountRecord fromString(String value) {
                        return null;
                    }
                };

        view.getAccountSelector().setConverter(converter);

        view.getAccountSelector().setCellFactory(listView ->
                new ListCell<>() {

                    @Override
                    protected void updateItem(
                            AccountRecord account,
                            boolean empty) {

                        super.updateItem(account, empty);

                        if (empty || account == null) {
                            setText(null);
                        } else {
                            setText(converter.toString(account));
                        }
                    }
                });

        view.getAccountSelector().setButtonCell(new ListCell<>() {

            @Override
            protected void updateItem(
                    AccountRecord account,
                    boolean empty) {

                super.updateItem(account, empty);

                if (empty || account == null) {
                    setText("Select an account");
                } else {
                    setText(converter.toString(account));
                }
            }
        });
    }

    // Connect dashboard buttons and account selection.
    private void connectEvents() {

        view.getAccountSelector()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldAccount, newAccount) -> {

                    if (newAccount != null) {
                        loadTransactionHistory(newAccount);
                    } else {
                        view.getTransactionTable().getItems().clear();
                    }
                });

        // Selecting an account in the table also selects it in the combo box.
        view.getAccountTable()
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldAccount, newAccount) -> {

                    if (newAccount != null) {
                        view.getAccountSelector().getSelectionModel()
                                .select(newAccount);
                    }
                });

        view.getDepositButton().setOnAction(event ->
                processTransaction("DEPOSIT"));

        view.getWithdrawButton().setOnAction(event ->
                processTransaction("WITHDRAWAL"));
    }

    // Load only accounts belonging to the logged-in customer.
    private void loadAccounts() {

        int customerID = user.getCustomerID();

        List<AccountRecord> accounts =
                accountDAO.getAccountRecordsByCustomerID(customerID);

        view.getAccountTable().setItems(
                FXCollections.observableArrayList(accounts)
        );

        view.getAccountSelector().setItems(
                FXCollections.observableArrayList(accounts)
        );

        if (!accounts.isEmpty()) {

            view.getAccountSelector()
                    .getSelectionModel()
                    .selectFirst();

        } else {

            view.getTransactionTable().getItems().clear();

            showInformation(
                    "No accounts found",
                    "You do not have any bank accounts yet. Please contact the bank administrator."
            );
        }
    }

    // Load transaction history for the selected customer's account.
    private void loadTransactionHistory(AccountRecord account) {

        List<TransactionRecord> transactions =
                transactionDAO.getTransactionsByCustomer(
                        user.getCustomerID(),
                        account.getAccountID()
                );

        view.getTransactionTable().setItems(
                FXCollections.observableArrayList(transactions)
        );
    }

    // Process deposits and withdrawals.
    private void processTransaction(String transactionType) {

        AccountRecord selectedAccount =
                view.getAccountSelector().getValue();

        if (selectedAccount == null) {
            showError("Please select an account first.");
            return;
        }

        String amountText =
                view.getAmountField().getText().trim();

        String description =
                view.getDescriptionField().getText().trim();

        if (amountText.isEmpty()) {
            showError("Please enter an amount.");
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountText);

        } catch (NumberFormatException e) {
            showError("Please enter a valid numerical amount.");
            return;
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            showError("The amount must be greater than zero.");
            return;
        }

        if (description.isEmpty()) {
            description = transactionType.equals("DEPOSIT")
                    ? "Customer deposit"
                    : "Customer withdrawal";
        }

        boolean successful =
                accountDAO.processCustomerTransaction(
                        user.getCustomerID(),
                        selectedAccount.getAccountID(),
                        transactionType,
                        amount,
                        description
                );

        if (successful) {

            int selectedAccountID =
                    selectedAccount.getAccountID();

            // Reload balances and account information.
            loadAccounts();

            // Keep the same account selected.
            for (AccountRecord account :
                    view.getAccountSelector().getItems()) {

                if (account.getAccountID() == selectedAccountID) {

                    view.getAccountSelector()
                            .getSelectionModel()
                            .select(account);

                    break;
                }
            }

            view.getAmountField().clear();
            view.getDescriptionField().clear();

            showInformation(
                    "Transaction Successful",
                    transactionType.equals("DEPOSIT")
                            ? "Your deposit was completed successfully."
                            : "Your withdrawal was completed successfully."
            );

        } else {

            showError(
                    "The transaction could not be completed. " +
                            "Check the account balance and account restrictions."
            );
        }
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Banking System");
        alert.setHeaderText("Unable to continue");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInformation(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Banking System");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


