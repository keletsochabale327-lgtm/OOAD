
package banking.model;

public class User {

    private int userID;
    private String email;
    private String passwordHash;
    private String role;
    private Integer customerID;

    public User(
            int userID,
            String email,
            String passwordHash,
            String role,
            Integer customerID
    ) {
        this.userID = userID;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public boolean isCustomer() {
        return "CUSTOMER".equalsIgnoreCase(role);
    }
}
