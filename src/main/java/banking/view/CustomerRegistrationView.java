
package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CustomerRegistrationView extends ScrollPane {

    // Input fields for basic customer information
    private final ComboBox<String> customerTypeBox = new ComboBox<>();
    private final TextField firstNameField = new TextField();
    private final TextField surnameField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField phoneField = new TextField();
    private final TextField addressField = new TextField();

    // Fields containing additional individual customer information
    private final TextField idNumberField = new TextField();
    private final DatePicker dateOfBirthPicker = new DatePicker();
    private final TextField employerNameField = new TextField();
    private final TextField employerAddressField = new TextField();

    // Fields containing company customer information
    private final TextField companyNameField = new TextField();
    private final TextField registrationNumberField = new TextField();
    private final TextField contactPersonField = new TextField();
    private final TextField businessAddressField = new TextField();

    private final Button registerButton = new Button("Register Customer");

    private final Label firstNameLabel = new Label("First Name");
    private final Label surnameLabel = new Label("Surname");

    // Separate sections allow the form to display individual or company details
    private final VBox individualSection;
    private final VBox companySection;

    public CustomerRegistrationView() {

        Label title = new Label("Customer Registration");
        title.getStyleClass().add("page-title");

        // Allow the administrator to choose the type of customer being registered
        customerTypeBox.getItems().addAll("INDIVIDUAL", "COMPANY");
        customerTypeBox.setValue("INDIVIDUAL");

        // Prompt text provides examples and guidance inside empty input fields
        firstNameField.setPromptText("e.g. Mary");
        surnameField.setPromptText("e.g. Jones");
        emailField.setPromptText("name@example.com");
        phoneField.setPromptText("Digits only, e.g. 71234567");
        addressField.setPromptText("Residential / postal address");
        idNumberField.setPromptText("Omang / ID number (digits only)");
        employerNameField.setPromptText("Needed for a Cheque account");
        employerAddressField.setPromptText("Needed for a Cheque account");
        companyNameField.setPromptText("Registered company name");
        registrationNumberField.setPromptText("Company registration number");
        contactPersonField.setPromptText("Full name of contact person");
        businessAddressField.setPromptText("Business address");

        // Create the basic details section using a GridPane layout
        GridPane basic = grid();

        addRow(basic, 0, new Label("Customer Type"), customerTypeBox);
        addRow(basic, 1, firstNameLabel, firstNameField);
        addRow(basic, 2, surnameLabel, surnameField);
        addRow(basic, 3, new Label("Email"), emailField);
        addRow(basic, 4, new Label("Phone"), phoneField);
        addRow(basic, 5, new Label("Address"), addressField);

        // Arrange fields specific to individual customers
        GridPane ind = grid();

        addRow(ind, 0, new Label("ID Number"), idNumberField);
        addRow(ind, 1, new Label("Date of Birth"), dateOfBirthPicker);
        addRow(ind, 2, new Label("Employer Name"), employerNameField);
        addRow(ind, 3, new Label("Employer Address"), employerAddressField);

        Label indHint = new Label(
                "Employer details are required before this customer can open a Cheque account."
        );
        indHint.getStyleClass().add("field-hint");

        individualSection = card("Individual Details", ind, indHint);

        // Arrange fields specific to company customers
        GridPane comp = grid();

        addRow(comp, 0, new Label("Company Name"), companyNameField);
        addRow(comp, 1, new Label("Registration No."), registrationNumberField);
        addRow(comp, 2, new Label("Contact Person"), contactPersonField);
        addRow(comp, 3, new Label("Business Address"), businessAddressField);

        companySection = card("Company Details", comp);

        VBox basicCard = card("Basic Details", basic);

        Label required = new Label(
                "Please complete all required fields."
        );
        required.getStyleClass().add("field-hint");

        registerButton.getStyleClass().add("primary-button");
        registerButton.setDefaultButton(true);

        // Arrange all registration sections vertically on the page
        VBox page = new VBox(
                16,
                title,
                basicCard,
                individualSection,
                companySection,
                required,
                registerButton
        );

        page.setPadding(new Insets(24));
        page.setMaxWidth(680);
        page.setAlignment(Pos.TOP_LEFT);

        StackPane holder = new StackPane(page);
        holder.setAlignment(Pos.TOP_CENTER);

        // Make the registration form scrollable when it exceeds the window size
        setContent(holder);
        setFitToWidth(true);

        // Update the visible form sections whenever the customer type changes
        customerTypeBox.setOnAction(event -> updateFields());
        updateFields();
    }

    // Show the fields that correspond to the selected customer type
    private void updateFields() {

        boolean individual =
                "INDIVIDUAL".equals(customerTypeBox.getValue());

        individualSection.setVisible(individual);
        individualSection.setManaged(individual);

        companySection.setVisible(!individual);
        companySection.setManaged(!individual);

        // Change the labels to reflect the selected customer type
        firstNameLabel.setText(
                individual ? "First Name" : "Contact First Name"
        );

        surnameLabel.setText(
                individual ? "Surname" : "Contact Surname"
        );
    }

    // Clear all input fields after customer registration
    public void clearForm() {

        for (TextField f : new TextField[]{
                firstNameField, surnameField, emailField,
                phoneField, addressField, idNumberField,
                employerNameField, employerAddressField,
                companyNameField, registrationNumberField,
                contactPersonField, businessAddressField
        }) {
            f.clear();
        }

        dateOfBirthPicker.setValue(null);
    }

    // Display messages such as successful registration or validation errors
    public void showMessage(Alert.AlertType type, String message) {

        Alert alert = new Alert(type);
        alert.setTitle("Customer Registration");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Reusable method for creating a card with a heading and content
    private static VBox card(String heading, Node... content) {

        Label h = new Label(heading);
        h.getStyleClass().add("section-title");

        VBox box = new VBox(12, h);
        box.getChildren().addAll(content);
        box.getStyleClass().add("card");

        return box;
    }

    // Create a GridPane with consistent spacing and column widths
    private static GridPane grid() {

        GridPane g = new GridPane();
        g.setHgap(14);
        g.setVgap(12);

        ColumnConstraints labels = new ColumnConstraints(150);
        ColumnConstraints inputs = new ColumnConstraints();

        // Allow the input column to expand when more space is available
        inputs.setHgrow(Priority.ALWAYS);
        g.getColumnConstraints().addAll(labels, inputs);

        return g;
    }

    // Add a label and its corresponding input control to a grid row
    private static void addRow(
            GridPane g,
            int row,
            Label label,
            Control input
    ) {

        label.getStyleClass().add("field-label");
        input.setMaxWidth(Double.MAX_VALUE);

        g.add(label, 0, row);
        g.add(input, 1, row);
    }

    public ComboBox<String> getCustomerTypeBox() {
        return customerTypeBox;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getSurnameField() {
        return surnameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public TextField getIdNumberField() {
        return idNumberField;
    }

    public DatePicker getDateOfBirthPicker() {
        return dateOfBirthPicker;
    }

    public TextField getEmployerNameField() {
        return employerNameField;
    }

    public TextField getEmployerAddressField() {
        return employerAddressField;
    }

    public TextField getCompanyNameField() {
        return companyNameField;
    }

    public TextField getRegistrationNumberField() {
        return registrationNumberField;
    }

    public TextField getContactPersonField() {
        return contactPersonField;
    }

    public TextField getBusinessAddressField() {
        return businessAddressField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }
}
