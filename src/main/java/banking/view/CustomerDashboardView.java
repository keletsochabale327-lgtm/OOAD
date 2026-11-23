
package banking.view;

import banking.model.User;
import banking.model.AccountRecord;
import banking.model.TransactionRecord;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CustomerDashboardView extends BorderPane {

    private final TableView<AccountRecord> accountTable =
            new TableView<>();

    private final TableView<TransactionRecord> transactionTable =
            new TableView<>();

    private final ComboBox<AccountRecord> accountSelector =
            new ComboBox<>();

    private final TextField amountField = new TextField();
    private final TextField descriptionField = new TextField();

    private final Button depositButton = new Button("Deposit");
    private final Button withdrawButton = new Button("Withdraw");
    private final Button logoutButton = new Button("Logout");

    private final Label welcomeLabel = new Label();

    public CustomerDashboardView(User user) {

        getStyleClass().add("app-root");
        setPadding(new Insets(20));

        // Header
        welcomeLabel.setText("Welcome, " + user.getEmail());
        welcomeLabel.getStyleClass().add("app-title");

        Label subtitle = new Label("Your Personal Banking Dashboard");
        subtitle.getStyleClass().add("app-subtitle");

        Label customerInfo = new Label(
                "Customer ID: " + user.getCustomerID()
        );

        VBox heading = new VBox(6, welcomeLabel, subtitle, customerInfo);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        logoutButton.getStyleClass().add("secondary-button");

        HBox header = new HBox(15, heading, spacer, logoutButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 25, 20, 25));
        header.getStyleClass().add("app-header");

        setTop(header);

        // Accounts table
        TableColumn<AccountRecord, Object> accountNumberColumn =
                new TableColumn<>("Account Number");

        accountNumberColumn.setCellValueFactory(
                new PropertyValueFactory<>("accountNumber")
        );

        TableColumn<AccountRecord, Object> accountTypeColumn =
                new TableColumn<>("Account Type");

        accountTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("accountType")
        );

        TableColumn<AccountRecord, Object> balanceColumn =
                new TableColumn<>("Balance (BWP)");

        balanceColumn.setCellValueFactory(
                new PropertyValueFactory<>("balance")
        );

        TableColumn<AccountRecord, Object> branchColumn =
                new TableColumn<>("Branch");

        branchColumn.setCellValueFactory(
                new PropertyValueFactory<>("branch")
        );

        accountTable.getColumns().addAll(
                accountNumberColumn,
                accountTypeColumn,
                balanceColumn,
                branchColumn
        );

        accountTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        accountTable.setPlaceholder(
                new Label("No accounts are currently available.")
        );

        // Transaction history table
        TableColumn<TransactionRecord, Object> typeColumn =
                new TableColumn<>("Transaction Type");

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );

        TableColumn<TransactionRecord, Object> amountColumn =
                new TableColumn<>("Amount (BWP)");

        amountColumn.setCellValueFactory(
                new PropertyValueFactory<>("amount")
        );

        TableColumn<TransactionRecord, Object> dateColumn =
                new TableColumn<>("Date");

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );

        TableColumn<TransactionRecord, Object> balanceAfterColumn =
                new TableColumn<>("Balance After");

        balanceAfterColumn.setCellValueFactory(
                new PropertyValueFactory<>("balanceAfter")
        );

        TableColumn<TransactionRecord, Object> descriptionColumn =
                new TableColumn<>("Description");

        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );

        transactionTable.getColumns().addAll(
                typeColumn,
                amountColumn,
                dateColumn,
                balanceAfterColumn,
                descriptionColumn
        );

        transactionTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        transactionTable.setPlaceholder(
                new Label("No transactions are available yet.")
        );

        // Account selector
        accountSelector.setPromptText("Select an account");
        accountSelector.setMaxWidth(Double.MAX_VALUE);

        // Transaction form
        Label transactionHeading = new Label("Make a Transaction");
        transactionHeading.getStyleClass().add("app-title");

        Label instruction = new Label(
                "Select an account and enter the transaction details."
        );

        amountField.setPromptText("Enter amount in BWP");
        descriptionField.setPromptText("Enter transaction description");

        depositButton.getStyleClass().add("primary-button");
        withdrawButton.getStyleClass().add("primary-button");

        depositButton.setMaxWidth(Double.MAX_VALUE);
        withdrawButton.setMaxWidth(Double.MAX_VALUE);

        HBox transactionButtons = new HBox(
                12,
                depositButton,
                withdrawButton
        );

        VBox transactionForm = new VBox(
                14,
                transactionHeading,
                instruction,
                new Label("Select Account"),
                accountSelector,
                new Label("Amount"),
                amountField,
                new Label("Description"),
                descriptionField,
                transactionButtons
        );

        transactionForm.setPadding(new Insets(25));
        transactionForm.getStyleClass().add("card");
        transactionForm.setMaxWidth(550);

        VBox transactionContainer = new VBox(
                15,
                transactionForm
        );

        transactionContainer.setAlignment(Pos.TOP_CENTER);
        transactionContainer.setPadding(new Insets(25));

        // Dashboard tabs
        Tab accountsTab = new Tab("My Accounts", accountTable);

        Tab transactionsTab = new Tab(
                "Transaction History",
                transactionTable
        );

        Tab actionsTab = new Tab(
                "Deposit / Withdraw",
                transactionContainer
        );

        accountsTab.setClosable(false);
        transactionsTab.setClosable(false);
        actionsTab.setClosable(false);

        TabPane tabPane = new TabPane(
                accountsTab,
                transactionsTab,
                actionsTab
        );

        tabPane.getStyleClass().add("banking-tabs");

        VBox mainContent = new VBox(18, tabPane);
        mainContent.setPadding(new Insets(20, 0, 0, 0));

        setCenter(mainContent);
    }

    public TableView<AccountRecord> getAccountTable() {
        return accountTable;
    }

    public TableView<TransactionRecord> getTransactionTable() {
        return transactionTable;
    }

    public ComboBox<AccountRecord> getAccountSelector() {
        return accountSelector;
    }

    public TextField getAmountField() {
        return amountField;
    }

    public TextField getDescriptionField() {
        return descriptionField;
    }

    public Button getDepositButton() {
        return depositButton;
    }

    public Button getWithdrawButton() {
        return withdrawButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}
