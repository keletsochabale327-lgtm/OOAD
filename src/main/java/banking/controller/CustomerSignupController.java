
package banking.controller;

import banking.dao.UserDAO;
import banking.view.CustomerSignupView;

public class CustomerSignupController {

    private final CustomerSignupView view;
    private final UserDAO userDAO;

    public CustomerSignupController(CustomerSignupView view) {

        this.view = view;
        this.userDAO = new UserDAO();

        view.getSignupButton().setOnAction(event -> registerCustomer());
    }

    private void registerCustomer() {

        String customerIDText = view.getCustomerID();
        String email = view.getEmail();
        String password = view.getPassword();
        String confirmPassword = view.getConfirmPassword();

        if (customerIDText.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            view.showMessage("Please complete all fields.");
            return;
        }

        int customerID;

        try {
            customerID = Integer.parseInt(customerIDText);
        } catch (NumberFormatException e) {
            view.showMessage("Please enter a valid customer ID.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showMessage("Passwords do not match.");
            return;
        }

        if (password.length() < 8) {
            view.showMessage(
                    "Your password must contain at least 8 characters."
            );
            return;
        }

        boolean registered = userDAO.registerCustomer(
                customerID,
                email,
                password
        );

        if (registered) {

            view.showMessage(
                    "Registration successful! You can now log in."
            );

            view.clearFields();

        } else {

            view.showMessage(
                    "Registration failed. Check your customer ID and registered email, or contact the administrator."
            );
        }
    }
}

