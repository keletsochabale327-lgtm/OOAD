
package banking;

public class CompanyCustomer extends Customer {

    private String companyName;
    private String registrationNumber;
    private String contactPerson;
    private String businessAddress;

    public CompanyCustomer(String customerID, String email,
            String address, int phone, String companyName,
            String registrationNumber, String contactPerson,
            String businessAddress) {

        super(customerID, email, address, phone);

        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.contactPerson = contactPerson;
        this.businessAddress = businessAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }
}
