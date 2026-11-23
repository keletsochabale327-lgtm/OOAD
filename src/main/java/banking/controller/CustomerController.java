
package banking.controller;

import banking.view.CustomerView;

public class CustomerController {

    private CustomerView customerView;

    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
    }

    public void displayCustomer(String customerID, String firstName,
                                String surname, String email, String address) {

        customerView.displayCustomerDetails(
                customerID,
                firstName,
                surname,
                email,
                address
        );
    }
}
