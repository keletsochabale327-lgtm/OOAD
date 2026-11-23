
package banking.view;

import banking.model.TransactionRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

public class TransactionView extends VBox {

    private final TableView<TransactionRecord> table;
    private final ObservableList<TransactionRecord> transactions;

    public TransactionView() {

        Label title = new Label("TRANSACTION HISTORY");
        title.getStyleClass().add("page-title");

        Label subtitle = new Label(
                "View deposits and withdrawals recorded for each account."
        );

        table = new TableView<>();
        transactions = FXCollections.observableArrayList();

        TableColumn<TransactionRecord, String> idColumn =
                new TableColumn<>("Transaction ID");
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("transactionID")
        );

        TableColumn<TransactionRecord, String> typeColumn =
                new TableColumn<>("Type");
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );

        TableColumn<TransactionRecord, Double> amountColumn =
                new TableColumn<>("Amount (BWP)");
        amountColumn.setCellValueFactory(
                new PropertyValueFactory<>("amount")
        );

        TableColumn<TransactionRecord, String> dateColumn =
                new TableColumn<>("Date");
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );

        TableColumn<TransactionRecord, Double> balanceColumn =
                new TableColumn<>("Balance After (BWP)");
        balanceColumn.setCellValueFactory(
                new PropertyValueFactory<>("balanceAfter")
        );

        TableColumn<TransactionRecord, String> descriptionColumn =
                new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );

        table.getColumns().addAll(
                idColumn,
                typeColumn,
                amountColumn,
                dateColumn,
                balanceColumn,
                descriptionColumn
        );

        table.setItems(transactions);
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        Label emptyMessage = new Label("No transactions to display.");
        table.setPlaceholder(emptyMessage);

        setSpacing(15);
        setPadding(new Insets(25));

        getChildren().addAll(title, subtitle, table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
    }

    public void loadTransactions(List<TransactionRecord> records) {
        transactions.setAll(records);
    }

    public TableView<TransactionRecord> getTable() {
        return table;
    }
}
