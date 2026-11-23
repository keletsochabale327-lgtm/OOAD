
package banking.controller;

import banking.model.AccountRecord;
import banking.dao.AccountDAO;
import banking.dao.TransactionDAO;
import banking.view.BankingTransactionView;
import banking.view.TransactionView;

public class TransactionController {

    private final BankingTransactionView transactionView;
    private final TransactionView historyView;

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public TransactionController(
            BankingTransactionView transactionView,
            TransactionView historyView) {

        this.transactionView = transactionView;
        this.historyView = historyView;

        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();

        loadAccounts();

        transactionView.getAccountComboBox()
                .valueProperty()
                .addListener((observable, oldAccount, newAccount) -> {
                    if (newAccount != null) {
                        updateSelectedAccount(newAccount);
                    }
                });

        transactionView.getProcessButton()
                .setOnAction(event -> processTransaction());
    }

    private void loadAccounts() {
        transactionView.loadAccounts(
                accountDAO.getAllAccountRecords()
        );
    }

    private void updateSelectedAccount(AccountRecord account) {

        transactionView.showBalance(account.getBalance());

        historyView.loadTransactions(
                transactionDAO.getTransactionsByAccount(
                        account.getAccountID()
                )
        );
    }

    private void processTransaction() {

        AccountRecord account = transactionView.getSelectedAccount();

        if (account == null) {
            transactionView.showMessage("Please select an account.", false);
            return;
        }

        String type = transactionView.getTransactionType();
        String amountText = transactionView.getAmount();
        String description = transactionView.getDescription();

        if (type == null || amountText.isEmpty()) {
            transactionView.showMessage(
                    "Select a transaction type and enter an amount.", false);
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountText);

            if (!Double.isFinite(amount) || amount <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            transactionView.showMessage(
                    "Enter a valid amount greater than zero.", false);
            return;
        }

        if (description.isEmpty()) {
            description = type.equals("DEPOSIT")
                    ? "Cash deposit"
                    : "Cash withdrawal";
        }

        boolean success = accountDAO.processTransaction(
                account.getAccountID(),
                type,
                amount,
                description
        );

        if (!success) {
            transactionView.showMessage(
                    "Transaction failed. Check the account rules and balance.",
                    false);
            return;
        }

        transactionView.showMessage(
                "Transaction completed successfully!", true);

        transactionView.clearFields();

        loadAccounts();

        for (AccountRecord updatedAccount :
                accountDAO.getAllAccountRecords()) {

            if (updatedAccount.getAccountID() == account.getAccountID()) {

                transactionView.getAccountComboBox().setValue(updatedAccount);
                updateSelectedAccount(updatedAccount);
                break;
            }
        }
    }
}
