
package banking;

public class InvestmentAccount extends Account implements InterestBearing {

    private final double interestRate = 0.05;
    private final double minimumDeposit = 500.00;

    public InvestmentAccount(String accountNumber, String branch,
                             double openingBalance) {

        super(accountNumber, branch, openingBalance);

        if (openingBalance < minimumDeposit) {
            throw new IllegalArgumentException(
                    "Minimum opening deposit is BWP 500.");
        }
    }

    @Override
    public void withdraw(double amount) {
        withdrawFromAccount(amount);
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
