
package banking;

public class Main {

    public static void main(String[] args) {

        // Create a Bank Administrator
        BankAdministrator admin = new BankAdministrator(
                "ADM001",
                "John",
                "Smith",
                77058483,
                "admin@bank.com",
                "Gaborone"
        );

        // Register an Individual Customer
        IndividualCustomer customer = new IndividualCustomer(
                "CUS001",
                "customer@email.com",
                "Gaborone",
                71234567,
                "Mary",
                "Jones",
                123456789,
                "2000-05-15",
                "ABC Company",
                "Gaborone"
        );

        admin.registerCustomer(customer);

        // Create a Savings Account
        SavingsAccount savings = new SavingsAccount(
                "ACC001",
                "Gaborone",
                1000.00
        );

        admin.createAccount(customer, savings);

        // Perform a deposit
        savings.deposit(200.00);

        try {
        savings.withdraw(100);
        } catch (UnsupportedOperationException e) {
        System.out.println("TEST PASSED: Savings Account does not allow withdrawals.");
        }

        // Pay monthly interest
        savings.payInterest();

        // Display account information
        System.out.println("BANKING SYSTEM");
        System.out.println("--------------------");

        System.out.println("Customer: "
                + customer.getFirstName() + " "
                + customer.getSurname());

        System.out.println("Account Number: "
                + savings.getAccountNumber());

        System.out.println("Account Type: "
                + savings.getAccountType());

        System.out.println("Current Balance: BWP "
                + savings.getBalance());

        // Display transaction history
        System.out.println("\nTRANSACTION HISTORY");

        for (Transaction transaction :
                customer.viewTransactionHistory(savings)) {

            System.out.println("--------------------");
            System.out.println(transaction);
        }

       // Test Investment Account
InvestmentAccount investment = new InvestmentAccount(
        "ACC002",
        "Gaborone",
        1000.00
);

admin.createAccount(customer, investment);

investment.deposit(500.00);
investment.withdraw(200.00);
investment.payInterest();

System.out.println("\nINVESTMENT ACCOUNT");
System.out.println("Balance: BWP " + investment.getBalance()); 

try {
    InvestmentAccount testInvestment =
            new InvestmentAccount("ACC004", "Gaborone", 400);

    System.out.println("TEST FAILED: Investment Account accepted less than BWP500.");

} catch (IllegalArgumentException e) {
    System.out.println("TEST PASSED: Investment Account rejects deposits below BWP500.");
}

try {
    ChequeAccount testCheque =
            new ChequeAccount("ACC005", "Gaborone", 1000, "", "");

    System.out.println("TEST FAILED: Cheque Account accepted missing employer details.");

} catch (IllegalArgumentException e) {
    System.out.println("TEST PASSED: Cheque Account requires employer details.");
}

try {
    savings.deposit(-100);

    System.out.println("TEST FAILED: Negative deposit was accepted.");

} catch (IllegalArgumentException e) {
    System.out.println("TEST PASSED: Negative deposits are rejected.");
}

// Test Cheque Account
ChequeAccount cheque = new ChequeAccount(
        "ACC003",
        "Gaborone",
        2000.00,
        "ABC Company",
        "Gaborone"
);

admin.createAccount(customer, cheque);

cheque.deposit(500.00);
cheque.deposit(100.00, "Salary payment");
cheque.withdraw(300.00);

System.out.println("\nCHEQUE ACCOUNT");
System.out.println("Employer: " + cheque.getEmployerName());
System.out.println("Balance: BWP " + cheque.getBalance());
    }
}
