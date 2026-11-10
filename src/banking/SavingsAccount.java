
package banking;

public class SavingsAccount extends Account implements InterestBearing {

    private final String interestRate = "0.05%";

    public SavingsAccount(String accountNumber, String branch,
                          double openingBalance) {

        super(accountNumber, branch, openingBalance);
    }

    @Override
    public double calculateInterest() {
    double rate = Double.parseDouble(
            interestRate.replace("%", "")) / 100;

    return getBalance() * rate;
    }

    @Override
    public void payInterest() {
        double interest = calculateInterest();
        addInterest(interest);
    }

    @Override
public void withdraw(double amount) {
    throw new UnsupportedOperationException(
            "Withdrawals are not allowed on Savings Accounts.");
}

@Override
public String getAccountType() {
    return "Savings Account";
}
}
