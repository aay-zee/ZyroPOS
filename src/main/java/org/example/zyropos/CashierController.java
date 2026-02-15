package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import database.dao.CashierDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class CashierController extends DashboardController implements Initializable {
    private String username;
    private int branchID;
    private CashierDAO cashierDAO;

    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: " + username);

        // Getting BranchID Corresponding to the username
        branchID = cashierDAO.getBranchID("Cashier", username);
        System.out.println("Branch ID: " + branchID);

        showDashboard();
    }

    @FXML
    private JFXButton btnCP;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXToggleButton btnToggle;

    @FXML
    private JFXButton btnVCGenerateBill;

    @FXML
    private JFXButton btnVCRemove;

    @FXML
    private JFXButton btnVPCart;

    @FXML
    private JFXButton btnVPSearch;

    @FXML
    private JFXButton btnViewCart;

    @FXML
    private JFXButton btnViewProducts;

    @FXML
    private JFXComboBox<String> cmbVPSearchColumn;

    @FXML
    private VBox cpPane;

    @FXML
    private VBox dashboardPane;

    @FXML
    private Label lblTodaySales;

    @FXML
    private Label lblTransactionCount;

    @FXML
    private Label lblProductsSold;

    @FXML
    private Label lblPerson;

    @FXML
    private BorderPane rootScene;

    @FXML
    private TableView<CashierProduct> tblCart;

    @FXML
    private TableView<CashierProduct> tblProducts;

    @FXML
    private TextField tfVPSearchVal;

    @FXML
    private BorderPane vcPane;

    @FXML
    private TableColumn<CashierProduct, Double> vpCartonPrice;

    @FXML
    private TableColumn<CashierProduct, Double> vcCartonPrice;

    @FXML
    private TableColumn<CashierProduct, String> vpCategory;

    @FXML
    private TableColumn<CashierProduct, String> vcCategory;

    @FXML
    private TableColumn<CashierProduct, Integer> vpID;

    @FXML
    private TableColumn<CashierProduct, Integer> vcID;

    @FXML
    private TableColumn<CashierProduct, String> vpName;

    @FXML
    private TableColumn<CashierProduct, String> vcName;

    @FXML
    private BorderPane vpPane;

    @FXML
    private TableColumn<CashierProduct, Integer> vpQuantity;

    @FXML
    private TableColumn<CashierProduct, Integer> vcQuantity;

    @FXML
    private TableColumn<CashierProduct, Double> vpSalePrice;

    @FXML
    private TableColumn<CashierProduct, Double> vcSalePrice;

    @FXML
    private TableColumn<CashierProduct, Double> vpUnitPrice;

    @FXML
    private TableColumn<CashierProduct, Double> vcUnitPrice;

    @FXML
    private TableView<Sale> tblRecentTransactions;
    @FXML
    private TableColumn<Sale, Integer> colSaleID;
    @FXML
    private TableColumn<Sale, Double> colAmount;
    @FXML
    private TableColumn<Sale, String> colDate;
    @FXML
    private TableColumn<Sale, String> colCashier;

    private ObservableList<CashierProduct> cartItems = FXCollections.observableArrayList();

    private String[] cols = { "ID", "Name", "Category", "Sale Price", "Unit Price", "Carton Price", "Quantity" };

    public CashierController() {
        cashierDAO = new CashierDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnViewProducts.setFocusTraversable(false);
        btnViewCart.setFocusTraversable(false);
        btnCP.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);

        cmbVPSearchColumn.getItems().addAll(cols);
    }

    private void setupProductTable() {
        vpID.setCellValueFactory(new PropertyValueFactory<>("id"));
        vpName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vpCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        vpSalePrice.setCellValueFactory(new PropertyValueFactory<>("SalePrice"));
        vpUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        vpCartonPrice.setCellValueFactory(new PropertyValueFactory<>("CartonPrice"));
        vpQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    }

    private void setupCartTable() {
        vcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        vcName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vcCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        vcSalePrice.setCellValueFactory(new PropertyValueFactory<>("SalePrice"));
        vcUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        vcCartonPrice.setCellValueFactory(new PropertyValueFactory<>("CartonPrice"));
        vcQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    }

    private void hideAllPanes() {
        if (dashboardPane != null)
            dashboardPane.setVisible(false);
        vpPane.setVisible(false);
        vcPane.setVisible(false);
        cpPane.setVisible(false);
        lblPerson.setVisible(false);
    }

    @FXML
    public void showDashboard() {
        hideAllPanes();
        dashboardPane.setVisible(true);

        try {
            double sales = cashierDAO.getTodaySales(branchID);
            int count = cashierDAO.getTransactionCount(branchID);

            lblTodaySales.setText(String.format("$%.2f", sales));
            lblTransactionCount.setText(String.valueOf(count));
            lblProductsSold.setText("-");

            // Populate Recent Transactions
            colSaleID.setCellValueFactory(new PropertyValueFactory<>("saleId"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
            colCashier.setCellValueFactory(new PropertyValueFactory<>("cashierUsername"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));

            // Format Amount
            colAmount.setCellFactory(tc -> new TableCell<Sale, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("$%.2f", item));
                    }
                }
            });

            tblRecentTransactions.setItems(cashierDAO.getRecentTransactions(branchID));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dislayVC() {
        hideAllPanes();
        setupCartTable();
        tblCart.setItems(cartItems);

        vcQuantity
                .setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        vcQuantity.setOnEditCommit(event -> {
            CashierProduct product = event.getRowValue();
            product.setQuantity(event.getNewValue());
        });

        vcPane.setVisible(true);
    }

    @FXML
    void displayCP(ActionEvent event) {
        hideAllPanes();
        cpPane.setVisible(true);
    }

    @FXML
    void displayVP(ActionEvent event) {
        hideAllPanes();

        setupProductTable();
        try {
            tblProducts.setItems(cashierDAO.getAllProducts(branchID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        vpPane.setVisible(true);
    }

    @FXML
    void generateBill(ActionEvent event) {
        if (cartItems.isEmpty()) {
            showAlert("Error", "Cart Empty", "Please add items to the cart.");
            return;
        }

        double totalBillAmount = 0.0;
        // Calculate total first
        for (CashierProduct product : cartItems) {
            totalBillAmount += product.getSalePrice() * product.getQuantity();
        }

        try {
            // 1. Create Sale Transaction FIRST to get the ID, now with correct total
            int saleId = cashierDAO.createSaleTransaction(totalBillAmount, branchID, username);

            // 2. Process items linked to this Sale ID
            for (CashierProduct product : cartItems) {
                int productId = product.getId();
                int quantity = product.getQuantity();
                double productTotal = product.getSalePrice() * quantity;

                // Update stock
                if (!cashierDAO.updateProductQuantity(productId, quantity, branchID)) {
                    throw new SQLException("Not enough stock for product: " + product.getName());
                }

                // Add Line Item with explicit Sale ID
                cashierDAO.addSaleLineItem(saleId, productId, quantity, productTotal, branchID);
            }

            // Clear the cart after successful sale
            cartItems.clear();
            tblCart.refresh();

            showAlert("Success", "Sale Complete", "Total Bill Amount: $" + String.format("%.2f", totalBillAmount));

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Transaction Failed", e.getMessage());
            return; // Don't print bill if failed
        }

        StringBuilder bill = new StringBuilder();
        String line = "------------------------------------------------\n";
        String doubleLine = "================================================\n";

        bill.append(doubleLine);
        bill.append(String.format("%30s\n", "ZYROPOS STORE")); // Centered-ish
        bill.append(String.format("%30s\n", "Branch: " + branchID));
        bill.append(doubleLine);

        bill.append(String.format(" Date: %-20s\n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        bill.append(String.format(" Cashier: %-20s\n", username));
        bill.append(line);

        bill.append(String.format(" %-20s %5s %10s\n", "Product", "Qty", "Price"));
        bill.append(line);

        for (CashierProduct product : cartItems) {
            bill.append(String.format(" %-20s %5d %10.2f\n",
                    truncateString(product.getName(), 20),
                    product.getQuantity(),
                    product.getSalePrice() * product.getQuantity()));
        }

        bill.append(line);
        bill.append(String.format(" TOTAL AMOUNT: %28.2f\n", totalBillAmount));
        bill.append(doubleLine);
        bill.append(String.format("%35s\n", "Thank You for Shopping!"));
        bill.append(String.format("%33s\n", "Visit Again Soon"));
        bill.append(doubleLine);

        displayBill(bill.toString());
    }

    private void displayBill(String billContent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Receipt Preview");
        alert.setHeaderText(null);
        alert.getDialogPane().setMinWidth(400);
        alert.getDialogPane().setMinHeight(500);

        // Apply CSS
        alert.getDialogPane().getStylesheets()
                .add(getClass().getResource("/org/example/zyropos/css/styles.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("my-dialog");

        TextArea textArea = new TextArea(billContent);
        textArea.setEditable(false);
        textArea.setFont(Font.font("Monospace", 12));
        textArea.setStyle("-fx-control-inner-background: white; -fx-font-family: 'Courier New';");
        textArea.setWrapText(true);

        ButtonType printButton = new ButtonType("Print Bill");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        // Use setAll to prevent duplicate OK buttons
        alert.getButtonTypes().setAll(printButton, okButton);

        alert.getDialogPane().setContent(textArea);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == printButton) {
            // Printing Bill
            printBill(billContent);
        }
    }

    private void printBill(String billContent) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            // Create a text node for printing to avoid scrollbars/borders
            javafx.scene.text.Text printText = new javafx.scene.text.Text(billContent);
            printText.setFont(Font.font("Monospace", 10)); // Slightly smaller for print

            boolean success = job.printPage(printText);
            if (success) {
                job.endJob();
            }
        }
    }

    private String truncateString(String str, int length) {
        if (str.length() > length) {
            return str.substring(0, length - 3) + "...";
        }
        return str;
    }

    @FXML
    void handleLogout(ActionEvent event) {
        try {
            logout(btnLogout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addToCart(ActionEvent event) {
        CashierProduct selectedProduct = tblProducts.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Check if product is already in cart
            for (CashierProduct item : cartItems) {
                if (item.getId() == selectedProduct.getId()) {
                    showAlert("Cart Error", "Product already in cart",
                            "Please update quantity in the cart or remove it first.");
                    return;
                }
            }

            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Quantity Selection");
            dialog.setHeaderText("Enter Quantity for " + selectedProduct.getName());
            dialog.setContentText("Quantity:");

            // Allow only numeric input
            dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    dialog.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int quantity = Integer.parseInt(result.get());

                    if (quantity <= 0) {
                        showAlert("Invalid Quantity", "Error", "Quantity must be greater than 0.");
                        return;
                    }

                    if (quantity > selectedProduct.getQuantity()) {
                        showAlert("Stock Error", "Insufficient Stock",
                                "Available stock: " + selectedProduct.getQuantity());
                        return;
                    }

                    // Create a copy for the cart to not mess up stock display in product table
                    CashierProduct cartItem = new CashierProduct(
                            selectedProduct.getId(),
                            selectedProduct.getName(),
                            selectedProduct.getCategory(),
                            selectedProduct.getSalePrice(),
                            selectedProduct.getUnitPrice(),
                            selectedProduct.getCartonPrice(),
                            quantity // Set the requested quantity
                    );

                    cartItems.add(cartItem);
                    showAlert("Success", "Product Added",
                            "Added " + quantity + " x " + selectedProduct.getName() + " to cart.");

                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Error", "Please enter a valid number.");
                }
            }
        } else {
            showAlert("Selection Error", "No Product Selected", "Please select a product first.");
        }
    }

    @FXML
    public void removeCartItem() {
        CashierProduct selectedProduct = tblCart.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            cartItems.remove(selectedProduct);
            tblCart.refresh();
        } else {
            showAlert("Cart Error", "No Selection", "Please select a product to remove.");
        }
    }

    @FXML
    void searchProducts(ActionEvent event) {
        String searchColumn = cmbVPSearchColumn.getValue();
        String searchValue = tfVPSearchVal.getText();

        if (searchColumn.equals("ID")) {
            searchColumn = "productID";
        } else if (searchColumn.equals("Name")) {
            searchColumn = "productName";
        } else if (searchColumn.equals("Category")) {
            searchColumn = "category";
        } else if (searchColumn.equals("Sale Price")) {
            searchColumn = "salePrice";
        } else if (searchColumn.equals("Unit Price")) {
            searchColumn = "unitPrice";
        } else if (searchColumn.equals("Carton Price")) {
            searchColumn = "cartonPrice";
        } else if (searchColumn.equals("Quantity")) {
            searchColumn = "quantity";
        }

        try {
            if (searchColumn != null && !searchValue.isEmpty()) {
                tblProducts.setItems(cashierDAO.searchProducts(branchID, searchColumn, searchValue));
            } else {
                // Reset to show all products if search field is empty
                tblProducts.setItems(cashierDAO.getAllProducts(branchID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
