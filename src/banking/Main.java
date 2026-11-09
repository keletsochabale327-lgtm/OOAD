
package banking;

public class Main {

    public static void main(String[] args) {

        // Create a Bank Administrator
        BankAdministrator admin = new BankAdministrator(
                "ADM001",
                "John",
                "Smith",
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
                "123456789",
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
    }
}
