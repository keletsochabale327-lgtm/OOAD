
package banking.view;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class AccountRegistrationView extends VBox {

    private final ComboBox<String> customerBox;
    private final TextField accountNumberField;
    private final ComboBox<String> accountTypeBox;
    private final TextField initialDepositField;
    private final TextField branchField;
    private final DatePicker openingDatePicker;
    private final Button openAccountButton;

    public AccountRegistrationView() {

        setSpacing(12);
        setStyle("-fx-padding: 20;");

        Label title = new Label("Account Registration");

        customerBox = new ComboBox<>();
        customerBox.setPromptText("Select Customer");

        accountNumberField = new TextField();
        accountNumberField.setPromptText("Account Number");

        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll(
                "SAVINGS",
                "CHEQUE",
                "INVESTMENT"
        );
        accountTypeBox.setPromptText("Select Account Type");

        initialDepositField = new TextField();
        initialDepositField.setPromptText("Initial Deposit");

        branchField = new TextField();
        branchField.setPromptText("Branch");

        openingDatePicker = new DatePicker();
        openingDatePicker.setPromptText("Opening Date");

        openAccountButton = new Button("Open Account");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);

        form.addRow(0, new Label("Customer:"), customerBox);
        form.addRow(1, new Label("Account Number:"), accountNumberField);
        form.addRow(2, new Label("Account Type:"), accountTypeBox);
        form.addRow(3, new Label("Initial Deposit:"), initialDepositField);
        form.addRow(4, new Label("Branch:"), branchField);
        form.addRow(5, new Label("Opening Date:"), openingDatePicker);

        getChildren().addAll(title, form, openAccountButton);
    }

    // Load customers
    public void setCustomers(List<String> customers) {
        customerBox.setItems(
                FXCollections.observableArrayList(customers)
        );
    }

    // Display alerts
    public void showMessage(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Account Registration");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear all form fields
    public void clearForm() {
        customerBox.getSelectionModel().clearSelection();
        accountNumberField.clear();
        accountTypeBox.getSelectionModel().clearSelection();
        initialDepositField.clear();
        branchField.clear();
        openingDatePicker.setValue(null);
    }

    public ComboBox<String> getCustomerBox() {
        return customerBox;
    }

    public TextField getAccountNumberField() {
        return accountNumberField;
    }

    public ComboBox<String> getAccountTypeBox() {
        return accountTypeBox;
    }

    public TextField getInitialDepositField() {
        return initialDepositField;
    }

    public TextField getBranchField() {
        return branchField;
    }

    public DatePicker getOpeningDatePicker() {
        return openingDatePicker;
    }

    public Button getOpenAccountButton() {
        return openAccountButton;
    }
}
