
package banking;

import java.util.ArrayList;
import java.util.List;

public class BankAdministrator {

    private String employeeID;
    private String firstName;
    private String surname;
    private String emailAddress;
    private String branch;

    private List<Customer> customers = new ArrayList<>();

    public BankAdministrator(String employeeID, String firstName,
                             String surname, String emailAddress,
                             String branch) {

        this.employeeID = employeeID;
        this.firstName = firstName;
        this.surname = surname;
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

        if (account instanceof ChequeAccount
                && !(customer instanceof IndividualCustomer)) {

            throw new IllegalArgumentException(
                    "Cheque Accounts are only available to working individuals.");
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getBranch() {
        return branch;
    }
}
