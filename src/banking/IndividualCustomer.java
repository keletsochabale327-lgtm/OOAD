
package banking;

public class IndividualCustomer extends Customer {

    private String firstName;
    private String surname;
    private String idNumber;
    private String dateOfBirth;
    private String employerName;
    private String employerAddress;

    public IndividualCustomer(String customerID, String email,
            String address, int phone, String firstName,
            String surname, String idNumber, String dateOfBirth,
            String employerName, String employerAddress) {

        super(customerID, email, address, phone);

        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }
}
