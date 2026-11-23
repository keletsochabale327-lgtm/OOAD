
package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CustomerView extends VBox {

    private Label customerIDLabel;
    private Label firstNameLabel;
    private Label surnameLabel;
    private Label emailLabel;
    private Label addressLabel;

    public CustomerView() {

        Label title = new Label("CUSTOMER DETAILS");

        customerIDLabel = new Label("Customer ID: ");
        firstNameLabel = new Label("First Name: ");
        surnameLabel = new Label("Surname: ");
        emailLabel = new Label("Email: ");
        addressLabel = new Label("Address: ");

        setSpacing(15);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);

        getChildren().addAll(
                title,
                customerIDLabel,
                firstNameLabel,
                surnameLabel,
                emailLabel,
                addressLabel
        );
    }

    public void displayCustomerDetails(
            String customerID,
            String firstName,
            String surname,
            String email,
            String address) {

        customerIDLabel.setText("Customer ID: " + customerID);
        firstNameLabel.setText("First Name: " + firstName);
        surnameLabel.setText("Surname: " + surname);
        emailLabel.setText("Email: " + email);
        addressLabel.setText("Address: " + address);
    }
}

