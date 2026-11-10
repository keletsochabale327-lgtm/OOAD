
package banking;

import java.util.ArrayList;
import java.util.List;

public class BankAdministrator {

    private String employeeID;
    private String firstName;
    private String surname;
    private int phone;
    private String emailAddress;
    private String branch;

    private List<Customer> customers = new ArrayList<>();

    public BankAdministrator(String employeeID, String firstName,
                             String surname, int phone, String emailAddress,
                             String branch) {

        this.employeeID = employeeID;
        this.firstName = firstName;
        this.surname = surname;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.branch = branch;
    }

    public void registerCustomer(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer cannot be null.");
        }

        for (Customer existing : customers) {
            if (existing.getCustomerID()
                    .equals(customer.getCustomerID())) {

                throw new IllegalArgumentException(
                        "Customer is already registered.");
            }
        }

        customers.add(customer);
    }

    public void createAccount(Customer customer, Account account) {

        if (!customers.contains(customer)) {
            throw new IllegalArgumentException(
                    "Customer must be registered first.");
        }

        if (account instanceof ChequeAccount) {

    if (!(customer instanceof IndividualCustomer)) {
        throw new IllegalArgumentException(
                "Cheque Accounts are only available to individuals.");
    }

    IndividualCustomer individual = (IndividualCustomer) customer;

    if (individual.getEmployerName() == null
            || individual.getEmployerName().isBlank()
            || individual.getEmployerAddress() == null
            || individual.getEmployerAddress().isBlank()) {

        throw new IllegalArgumentException(
                "Employer details are required for a Cheque Account.");
    }
}

        for (Customer existing : customers) {
            for (Account existingAccount : existing.getAccounts()) {
                if (existingAccount.getAccountNumber()
                        .equals(account.getAccountNumber())) {

                    throw new IllegalArgumentException(
                            "Account number already exists.");
                }
            }
        }

        customer.addAccount(account);
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getBranch() {
        return branch;
    }
}
