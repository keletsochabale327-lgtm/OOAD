
package banking.view;

import banking.model.AccountRecord;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

import java.util.List;

public class BankingTransactionView extends VBox {

    private ComboBox<AccountRecord> accountComboBox;
    private ComboBox<String> transactionTypeComboBox;
    private TextField amountField;
    private TextField descriptionField;
    private Label balanceLabel;
    private Label messageLabel;
    private Button processButton;

    public BankingTransactionView() {

        Label title = new Label("PROCESS TRANSACTION");
        title.getStyleClass().add("page-title");

        accountComboBox = new ComboBox<>();
        accountComboBox.setPromptText("Select an account");
        accountComboBox.setMaxWidth(Double.MAX_VALUE);

        transactionTypeComboBox = new ComboBox<>(
                FXCollections.observableArrayList("DEPOSIT", "WITHDRAWAL")
        );
        transactionTypeComboBox.setPromptText("Select transaction type");
        transactionTypeComboBox.setMaxWidth(Double.MAX_VALUE);

        amountField = new TextField();
        amountField.setPromptText("Enter amount in BWP");

        descriptionField = new TextField();
        descriptionField.setPromptText("Enter transaction description");

        balanceLabel = new Label("Current Balance: BWP 0.00");
        messageLabel = new Label();

        processButton = new Button("Process Transaction");
        processButton.getStyleClass().add("primary-button");

        VBox card = new VBox(15,
                new Label("Select Account"),
                accountComboBox,
                new Label("Transaction Type"),
                transactionTypeComboBox,
                new Label("Amount"),
                amountField,
                new Label("Description"),
                descriptionField,
                balanceLabel,
                processButton,
                messageLabel
        );

        card.getStyleClass().add("card");
        card.setMaxWidth(500);
        card.setPadding(new Insets(25));

        setSpacing(20);
        setPadding(new Insets(25));
        getChildren().addAll(title, card);
    }

    public void loadAccounts(List<AccountRecord> accounts) {
        accountComboBox.setItems(
                FXCollections.observableArrayList(accounts)
        );
    }

    public AccountRecord getSelectedAccount() {
        return accountComboBox.getValue();
    }

    public String getTransactionType() {
        return transactionTypeComboBox.getValue();
    }

    public String getAmount() {
        return amountField.getText().trim();
    }

    public String getDescription() {
        return descriptionField.getText().trim();
    }

    public void showBalance(double balance) {
        balanceLabel.setText(
                String.format("Current Balance: BWP %.2f", balance)
        );
    }

    public void showMessage(String message, boolean success) {
        messageLabel.setText(message);
        messageLabel.setStyle(
                success ? "-fx-text-fill: green;" :
                        "-fx-text-fill: red;"
        );
    }

    public void clearFields() {
        amountField.clear();
        descriptionField.clear();
        transactionTypeComboBox.setValue(null);
    }

    public Button getProcessButton() {
        return processButton;
    }

    public ComboBox<AccountRecord> getAccountComboBox() {
        return accountComboBox;
    }
}

