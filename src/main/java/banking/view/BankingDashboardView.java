
package banking.view;

import banking.controller.AdminTransactionHistoryController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BankingDashboardView extends BorderPane {

    private final TabPane tabPane = new TabPane();

    private final CustomerRegistrationView customerRegistrationView =
            new CustomerRegistrationView();

    private final AccountRegistrationView accountRegistrationView =
            new AccountRegistrationView();

    private final TransactionView transactionView =
            new TransactionView();

    private final Tab accountsTab;
    private final Tab historyTab;

    public BankingDashboardView() {

        getStyleClass().add("app-root");

        Label title = new Label("BANKING SYSTEM");
        title.getStyleClass().add("app-title");

        Label subtitle = new Label("Bank Administrator Portal");
        subtitle.getStyleClass().add("app-subtitle");

        VBox header = new VBox(6, title, subtitle);
        header.getStyleClass().add("app-header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(22, 30, 22, 30));

        Tab customersTab = new Tab(
                "Customers",
                customerRegistrationView
        );

        accountsTab = new Tab(
                "Accounts",
                accountRegistrationView
        );

        historyTab = new Tab(
                "Transaction History",
                transactionView
        );

        customersTab.setClosable(false);
        accountsTab.setClosable(false);
        historyTab.setClosable(false);

        tabPane.getTabs().addAll(
                customersTab,
                accountsTab,
                historyTab
        );

        tabPane.getStyleClass().add("banking-tabs");

        setTop(header);
        setCenter(tabPane);
        setPadding(new Insets(18));

        // Administrator can view history but cannot process transactions.
        new AdminTransactionHistoryController(transactionView);
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Tab getAccountsTab() {
        return accountsTab;
    }

    public Tab getHistoryTab() {
        return historyTab;
    }

    public CustomerRegistrationView getCustomerRegistrationView() {
        return customerRegistrationView;
    }

    public AccountRegistrationView getAccountRegistrationView() {
        return accountRegistrationView;
    }

    public TransactionView getTransactionView() {
        return transactionView;
    }
}
