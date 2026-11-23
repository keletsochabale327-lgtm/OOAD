
package banking.controller;

import banking.view.AccountView;

public class AccountController {

    private AccountView accountView;

    public AccountController(AccountView accountView) {
        this.accountView = accountView;
    }

    public void displayAccount(String accountNumber, String accountType,
                               double balance, String branch) {

        accountView.displayAccountDetails(
                accountNumber,
                accountType,
                balance,
                branch
        );
    }
}
