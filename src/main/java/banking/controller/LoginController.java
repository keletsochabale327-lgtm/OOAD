
package banking.controller;

import banking.dao.UserDAO;
import banking.model.User;
import banking.view.LoginView;

import java.util.function.Consumer;

public class LoginController {

    private final LoginView loginView;
    private final UserDAO userDAO;
    private final Consumer<User> onLoginSuccess;

    public LoginController(
            LoginView loginView,
            Consumer<User> onLoginSuccess
    ) {
        this.loginView = loginView;
        this.userDAO = new UserDAO();
        this.onLoginSuccess = onLoginSuccess;

        loginView.getLoginButton().setOnAction(event -> login());
    }

    private void login() {

        String selectedRole = loginView.getSelectedRole();
        String email = loginView.getEmail();
        String password = loginView.getPassword();

        if (selectedRole == null) {
            loginView.showError(
                    "Please select Administrator Login or Customer Login."
            );
            return;
        }

        if (email.isEmpty() || password.isEmpty()) {
            loginView.showError(
                    "Please enter your email and password."
            );
            return;
        }

        User user = userDAO.authenticate(email, password);

        if (user == null) {
            loginView.showError(
                    "Incorrect email or password."
            );
            loginView.clearPassword();
            return;
        }

        if ("ADMIN".equals(selectedRole) && !user.isAdmin()) {
            loginView.showError(
                    "This account is not registered as an administrator."
            );
            loginView.clearPassword();
            return;
        }

        if ("CUSTOMER".equals(selectedRole) && !user.isCustomer()) {
            loginView.showError(
                    "This account is not registered as a customer."
            );
            loginView.clearPassword();
            return;
        }

        onLoginSuccess.accept(user);
    }
}
