
package banking;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {

    private String customerID;
    private String email;
    private String address;
    private int phone;

    private List<Account> accounts = new ArrayList<>();

    public Customer(String customerID, String email,
                    String address, int phone) {
        this.customerID = customerID;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public List<Transaction> viewTransactionHistory(Account account) {
        if (!accounts.contains(account)) {
            throw new IllegalArgumentException(
                    "Account does not belong to this customer.");
        }

        return account.getTransactions();
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }
}
