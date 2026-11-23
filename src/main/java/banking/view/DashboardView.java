
package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView extends VBox {

    private Button customerButton;
    private Button accountButton;
    private Button transactionButton;
    private Button logoutButton;

    public DashboardView() {

        Label title = new Label("BANKING SYSTEM DASHBOARD");
        Label welcomeMessage = new Label("Welcome to your banking system!");

        customerButton = new Button("Customer Details");
        accountButton = new Button("Account Details");
        transactionButton = new Button("Transaction History");
        logoutButton = new Button("Logout");

        setSpacing(20);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);

        getChildren().addAll(
                title,
                welcomeMessage,
                customerButton,
                accountButton,
                transactionButton,
                logoutButton
        );
    }

    public Button getCustomerButton() {
        return customerButton;
    }

    public Button getAccountButton() {
        return accountButton;
    }

    public Button getTransactionButton() {
        return transactionButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}

