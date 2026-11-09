
package banking;

public class SavingsAccount extends Account implements InterestBearing {

    private final double interestRate = 0.0005;

    public SavingsAccount(String accountNumber, String branch,
                          double openingBalance) {

        super(accountNumber, branch, openingBalance);
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }

    @Override
    public void payInterest() {
        double interest = calculateInterest();
        addInterest(interest);
    }
}
