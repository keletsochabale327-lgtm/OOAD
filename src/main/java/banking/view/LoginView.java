
package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginView extends StackPane {

    private final TextField emailField = new TextField();
    private final PasswordField passwordField = new PasswordField();

    private final Button loginButton = new Button("Login");

    private final Button signupButton =
            new Button("Create a Customer Account");

    private final Label errorLabel = new Label();

    private final ToggleGroup loginTypeGroup = new ToggleGroup();

    private final RadioButton adminOption =
            new RadioButton("Administrator Login");

    private final RadioButton customerOption =
            new RadioButton("Customer Login");

    public LoginView() {

        getStyleClass().add("app-root");

        Label title = new Label("BANKING SYSTEM");
        title.getStyleClass().add("login-title");

        Label subtitle = new Label(
                "Secure Online Banking Portal"
        );
        subtitle.getStyleClass().add("field-hint");

        // Login type selection
        Label loginTypeLabel = new Label("Select Login Type");

        adminOption.setToggleGroup(loginTypeGroup);
        customerOption.setToggleGroup(loginTypeGroup);

        adminOption.setUserData("ADMIN");
        customerOption.setUserData("CUSTOMER");

        VBox loginOptions = new VBox(
                10,
                adminOption,
                customerOption
        );

        emailField.setPromptText("Enter your email address");
        passwordField.setPromptText("Enter your password");

        loginButton.getStyleClass().add("primary-button");
        loginButton.setDefaultButton(true);
        loginButton.setMaxWidth(Double.MAX_VALUE);

        signupButton.setMaxWidth(Double.MAX_VALUE);

        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Label signupPrompt = new Label(
                "Not registered for online banking yet?"
        );

        signupPrompt.getStyleClass().add("field-hint");

        VBox card = new VBox(
                12,
                title,
                subtitle,

                new Separator(),

                loginTypeLabel,
                loginOptions,

                new Label("Email Address"),
                emailField,

                new Label("Password"),
                passwordField,

                errorLabel,
                loginButton,

                new Separator(),

                signupPrompt,
                signupButton
        );

        card.getStyleClass().add("card");
        card.setPadding(new Insets(32));
        card.setMaxWidth(420);
        card.setMaxHeight(Region.USE_PREF_SIZE);
        card.setAlignment(Pos.CENTER_LEFT);

        getChildren().add(card);
        setAlignment(Pos.CENTER);
    }

    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    public void clearPassword() {
        passwordField.clear();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getSelectedRole() {
        Toggle selected = loginTypeGroup.getSelectedToggle();

        if (selected == null) {
            return null;
        }

        return selected.getUserData().toString();
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getSignupButton() {
        return signupButton;
    }
}
