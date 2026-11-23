
package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AccountView extends VBox {

    private Label accountNumberLabel;
    private Label accountTypeLabel;
    private Label balanceLabel;
    private Label branchLabel;

    public AccountView() {

        Label title = new Label("ACCOUNT DETAILS");

        accountNumberLabel = new Label("Account Number: ");
        accountTypeLabel = new Label("Account Type: ");
        balanceLabel = new Label("Balance: ");
        branchLabel = new Label("Branch: ");

        setSpacing(15);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);

        getChildren().addAll(
                title,
                accountNumberLabel,
                accountTypeLabel,
                balanceLabel,
                branchLabel
        );
    }

    public void displayAccountDetails(
            String accountNumber,
            String accountType,
            double balance,
            String branch) {

        accountNumberLabel.setText("Account Number: " + accountNumber);
        accountTypeLabel.setText("Account Type: " + accountType);
        balanceLabel.setText("Balance: BWP " + balance);
        branchLabel.setText("Branch: " + branch);
    }
}
