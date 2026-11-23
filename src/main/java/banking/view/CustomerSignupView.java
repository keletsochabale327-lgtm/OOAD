
package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CustomerSignupView extends VBox {

    private final TextField customerIDField;
    private final TextField emailField;
    private final PasswordField passwordField;
    private final PasswordField confirmPasswordField;

    private final Button signupButton;
    private final Button backButton;

    private final Label messageLabel;

    public CustomerSignupView() {

        setSpacing(15);
        setPadding(new Insets(35));
        setAlignment(Pos.CENTER);

        setMaxWidth(450);

        Label title = new Label("Customer Registration");
        title.getStyleClass().add("form-title");

        Label description = new Label(
                "Create your banking system login account"
        );

        customerIDField = new TextField();
        customerIDField.setPromptText("Enter your customer ID");

        emailField = new TextField();
        emailField.setPromptText("Enter your registered email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Create a password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");

        signupButton = new Button("Create Account");
        signupButton.setMaxWidth(Double.MAX_VALUE);

        backButton = new Button("Back to Login");

        messageLabel = new Label();
        messageLabel.setWrapText(true);

        getChildren().addAll(
                title,
                description,
                customerIDField,
                emailField,
                passwordField,
                confirmPasswordField,
                signupButton,
                backButton,
                messageLabel
        );
    }

    public String getCustomerID() {
        return customerIDField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getConfirmPassword() {
        return confirmPasswordField.getText();
    }

    public Button getSignupButton() {
        return signupButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    public void clearFields() {
        customerIDField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
}
