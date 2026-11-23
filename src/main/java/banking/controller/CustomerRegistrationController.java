package banking.controller;

import banking.model.CompanyCustomer;
import banking.model.IndividualCustomer;
import banking.dao.CustomerDAO;
import banking.view.CustomerRegistrationView;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class CustomerRegistrationController {

    private final CustomerRegistrationView view;
    private final CustomerDAO customerDAO;

    public CustomerRegistrationController(CustomerRegistrationView view) {
        this.view = view;
        this.customerDAO = new CustomerDAO();

        view.getRegisterButton().setOnAction(event -> registerCustomer());
    }

    private void registerCustomer() {
        try {
            String type = view.getCustomerTypeBox().getValue();

            String firstName = view.getFirstNameField().getText().trim();
            String surname = view.getSurnameField().getText().trim();
            String email = view.getEmailField().getText().trim();
            String phoneText = view.getPhoneField().getText().trim();
            String address = view.getAddressField().getText().trim();

            if (firstName.isEmpty() || surname.isEmpty()
                    || phoneText.isEmpty() || address.isEmpty()) {
                view.showMessage(Alert.AlertType.WARNING,
                        "Please complete all required customer details.");
                return;
            }

            int phone = Integer.parseInt(phoneText);
            String customerID = "";
            String savedID = null;

            if ("INDIVIDUAL".equals(type)) {

                String idText = view.getIdNumberField().getText().trim();

                if (idText.isEmpty()) {
                    view.showMessage(Alert.AlertType.WARNING,
                            "Please enter the ID number.");
                    return;
                }

                int idNumber = Integer.parseInt(idText);

                LocalDate dob = view.getDateOfBirthPicker().getValue();
                String dateOfBirth = dob == null ? null : dob.toString();

                IndividualCustomer customer = new IndividualCustomer(
                        customerID, email, address, phone,
                        firstName, surname, idNumber, dateOfBirth,
                        view.getEmployerNameField().getText().trim(),
                        view.getEmployerAddressField().getText().trim());

                savedID = customerDAO.addIndividualCustomer(customer);

            } else if ("COMPANY".equals(type)) {

                String companyName = view.getCompanyNameField().getText().trim();
                String registrationNumber =
                        view.getRegistrationNumberField().getText().trim();
                String contactPerson = view.getContactPersonField().getText().trim();
                String businessAddress = view.getBusinessAddressField().getText().trim();

                if (companyName.isEmpty() || businessAddress.isEmpty()
                        || contactPerson.isEmpty()) {
                    view.showMessage(Alert.AlertType.WARNING,
                            "Please complete the required company details.");
                    return;
                }

                CompanyCustomer customer = new CompanyCustomer(
                        customerID, email, address, phone,
                        companyName, registrationNumber,
                        contactPerson, businessAddress);

                savedID = customerDAO.addCompanyCustomer(customer, firstName, surname);
            }

            if (savedID != null) {
                view.showMessage(Alert.AlertType.INFORMATION,
                        "Customer registered successfully. ID: " + savedID);
                view.clearForm();
            } else {
                view.showMessage(Alert.AlertType.ERROR,
                        "The customer could not be saved. Check the details and try again.");
            }

        } catch (NumberFormatException e) {
            view.showMessage(Alert.AlertType.ERROR,
                    "Phone number and ID number must contain digits only.");
        } catch (Exception e) {
            view.showMessage(Alert.AlertType.ERROR,
                    "Could not register customer: " + e.getMessage());
        }
    }
}