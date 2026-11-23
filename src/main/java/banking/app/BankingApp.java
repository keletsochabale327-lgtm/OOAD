
package banking.app;

import banking.controller.AccountRegistrationController;
import banking.controller.CustomerRegistrationController;
import banking.controller.CustomerSignupController;
import banking.controller.CustomerDashboardController;
import banking.controller.LoginController;

import banking.model.User;

import banking.view.BankingDashboardView;
import banking.view.CustomerDashboardView;
import banking.view.CustomerSignupView;
import banking.view.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class BankingApp extends Application {

    private Scene scene;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {

        primaryStage = stage;

        LoginView loginView = new LoginView();

        scene = new Scene(loginView, 1000, 700);

        loadStylesheet();

        showLogin();

        stage.setTitle("Banking System");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private void loadStylesheet() {

        try {

            var css = getClass().getResource("/banking/styles.css");

            if (css != null) {
                scene.getStylesheets().add(css.toExternalForm());
                System.out.println("Banking stylesheet loaded successfully.");
            } else {
                System.out.println("ERROR: styles.css was not found.");
            }

        } catch (Exception e) {
            System.out.println(
                    "Could not load stylesheet: " + e.getMessage()
            );
        }
    }

    private void showLogin() {

        LoginView loginView = new LoginView();

        new LoginController(loginView, this::handleLogin);

        loginView.getSignupButton().setOnAction(
                event -> showSignup()
        );

        scene.setRoot(loginView);
    }

    private void showSignup() {

        CustomerSignupView signupView = new CustomerSignupView();

        new CustomerSignupController(signupView);

        signupView.getBackButton().setOnAction(
                event -> showLogin()
        );

        scene.setRoot(signupView);
    }

    private void handleLogin(User user) {

        if (user.isAdmin()) {

            showAdminDashboard();

        } else if (user.isCustomer()) {

            showCustomerDashboard(user);

        } else {

            showError("Unrecognised user role.");
        }
    }

    private void showCustomerDashboard(User user) {

        CustomerDashboardView dashboard =
                new CustomerDashboardView(user);

        new CustomerDashboardController(dashboard, user);

        dashboard.getLogoutButton().setOnAction(
                event -> showLogin()
        );

        scene.setRoot(dashboard);
    }

    private void showAdminDashboard() {

        BankingDashboardView dashboard =
                new BankingDashboardView();

        new CustomerRegistrationController(
                dashboard.getCustomerRegistrationView()
        );

        new AccountRegistrationController(
                dashboard.getAccountRegistrationView()
        );

        scene.setRoot(dashboard);
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Login Error");
        alert.setHeaderText("Unable to continue");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
