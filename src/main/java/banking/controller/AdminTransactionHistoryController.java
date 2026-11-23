
package banking.controller;

import banking.dao.AccountDAO;
import banking.dao.TransactionDAO;
import banking.model.AccountRecord;
import banking.model.TransactionRecord;
import banking.view.TransactionView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AdminTransactionHistoryController {

    private final TransactionView view;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public AdminTransactionHistoryController(TransactionView view) {

        this.view = view;
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();

        loadAllTransactions();
    }

    public void loadAllTransactions() {

        List<TransactionRecord> allTransactions = new ArrayList<>();

        List<AccountRecord> accounts =
                accountDAO.getAllAccountRecords();

        for (AccountRecord account : accounts) {

            List<TransactionRecord> accountTransactions =
                    transactionDAO.getTransactionsByAccount(
                            account.getAccountID()
                    );

            allTransactions.addAll(accountTransactions);
        }

        allTransactions.sort(
                Comparator.comparing(TransactionRecord::getDate).reversed()
        );

        view.loadTransactions(allTransactions);
    }
}
