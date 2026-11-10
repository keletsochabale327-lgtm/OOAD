
package banking;

public class ChequeAccount extends Account {

    private String employerName;
    private String employerAddress;

    public ChequeAccount(String accountNumber, String branch,
                         double openingBalance, String employerName,
                         String employerAddress) {

        super(accountNumber, branch, openingBalance);

        if (employerName == null || employerName.isBlank()
                || employerAddress == null || employerAddress.isBlank()) {

            throw new IllegalArgumentException(
                    "Employer name and address are required.");
        }

        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public void withdraw(double amount) {
        withdrawFromAccount(amount);
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    @Override
public String getAccountType() {
    return "Cheque Account";
}
}
