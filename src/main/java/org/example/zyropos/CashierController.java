package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import database.model.CashierModel;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class CashierController extends  DashboardController implements Initializable {
    private String username;
    private int branchID;
    private CashierModel cashierModel;


    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: "+username);

        //Getting BranchID Corresponding to the username
        branchID = cashierModel.getBranchID(username);
        System.out.println("Branch ID: "+branchID);
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
    private BorderPane cpPane;

    @FXML
    private Pane innerPane1;

    @FXML
    private Label lblPerson;

    @FXML
    private HBox rootScene;

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

    private ObservableList<CashierProduct> cartItems= FXCollections.observableArrayList();

    private String[] cols={"ID","Name","Category","Sale Price","Unit Price","Carton Price","Quantity"};

    public CashierController() {
        cashierModel=new CashierModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            checkFirstTimeLogin("Cashier",username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnViewProducts.setFocusTraversable(false);
        btnViewCart.setFocusTraversable(false);
        btnCP.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);

        cmbVPSearchColumn.getItems().addAll(cols);
    }

    private void setupProductTable(){
        vpID.setCellValueFactory(new PropertyValueFactory<>("id"));
        vpName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vpCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        vpSalePrice.setCellValueFactory(new PropertyValueFactory<>("SalePrice"));
        vpUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        vpCartonPrice.setCellValueFactory(new PropertyValueFactory<>("CartonPrice"));
        vpQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    }

    private void setupCartTable(){
        vcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        vcName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vcCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        vcSalePrice.setCellValueFactory(new PropertyValueFactory<>("SalePrice"));
        vcUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        vcCartonPrice.setCellValueFactory(new PropertyValueFactory<>("CartonPrice"));
        vcQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    }

    @FXML
    void dislayVC() {
        lblPerson.setVisible(false);
        lblPerson.setVisible(false);
        cpPane.setVisible(false);
        setupCartTable();
        tblCart.setItems(cartItems);

        vcQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        vcQuantity.setOnEditCommit(event -> {
            CashierProduct product = event.getRowValue();
            product.setQuantity(event.getNewValue());
        });

        vcPane.setVisible(true);
    }

    @FXML
    void displayCP(ActionEvent event) {
        lblPerson.setVisible(false);
        vpPane.setVisible(false);
        vcPane.setVisible(false);
        cpPane.setVisible(true);
    }

    @FXML
    void displayVP(ActionEvent event) {
        lblPerson.setVisible(false);
        vcPane.setVisible(false);
        cpPane.setVisible(false);

        setupProductTable();
        try {
            tblProducts.setItems(cashierModel.getAllProducts(branchID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        vpPane.setVisible(true);
    }

    @FXML
    void generateBill(ActionEvent event) {
        double totalBillAmount = 0.0;


        for (CashierProduct product : cartItems) {
            int productId = product.getId();
            int quantity = product.getQuantity();
            double productTotal = product.getSalePrice() * quantity;

            totalBillAmount += productTotal;

            try {
                // Update product quantity in database
                if(!(cashierModel.updateProductQuantity(productId, quantity, branchID))){
                    showAlert("Error", "Database Error", "Not enough stock available for product: " + product.getName());
                }

                // Add individual sale record
                cashierModel.addSaleRecord(productId, quantity, productTotal, branchID);
            } catch (SQLException e) {
                showAlert("Error", "Database Error", "Failed to process sale: " + e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            // Add the complete sale transaction
            cashierModel.addSaleTransaction(totalBillAmount, branchID, username);

            // Clear the cart after successful sale
            cartItems.clear();
            tblCart.refresh();

            showAlert("Success", "Sale Complete", "Total Bill Amount: $" + totalBillAmount);
        } catch (SQLException e) {
            showAlert("Error", "Database Error", "Failed to complete transaction: " + e.getMessage());
        }


        StringBuilder bill = new StringBuilder();


        bill.append("==========================================\n");
        bill.append("              ZYROPOS BILL               \n");
        bill.append("==========================================\n\n");


        bill.append(String.format("Date: %s\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        bill.append(String.format("Cashier: %s\n", username));
        bill.append(String.format("Branch ID: %d\n", branchID));
        bill.append("------------------------------------------\n");


        bill.append(String.format("%-20s %8s %10s\n", "Product", "Qty", "Amount"));
        bill.append("------------------------------------------\n");

        for (CashierProduct product : cartItems) {
            bill.append(String.format("%-20s %8d %10.2f\n",
                    truncateString(product.getName(), 20),
                    product.getQuantity(),
                    product.getSalePrice() * product.getQuantity()));
        }


        bill.append("------------------------------------------\n");
        bill.append(String.format("Total Amount: %24.2f\n", totalBillAmount));
        bill.append("==========================================\n");
        bill.append("          Thank You, Visit Again!         \n");
        bill.append("==========================================\n");

        displayBill(bill.toString());
    }

    private void displayBill(String billContent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sales Receipt");
        alert.setHeaderText(null);
        alert.getDialogPane().setMinWidth(450);
        alert.getDialogPane().setMinHeight(600);

        TextArea textArea = new TextArea(billContent);
        textArea.setEditable(false);
        textArea.setFont(Font.font("Monospace", 12));
        textArea.setStyle("-fx-control-inner-background: white;");

        ButtonType printButton=new ButtonType("Print Bill");
        ButtonType okButton=new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().addAll(printButton,okButton);

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
            TextArea printArea = new TextArea(billContent);
            printArea.setFont(Font.font("Monospace", 12));
            job.printPage(printArea);
            job.endJob();
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
        CashierProduct selectedProduct=tblProducts.getSelectionModel().getSelectedItem();

        if(selectedProduct!=null){
            cartItems.add(selectedProduct);
            showAlert("Product Addition","Product Addition Success","Product Added to Cart");
        }
        else{
            showAlert("Product Selection","Product not selected","Please Select a Product First");
        }
    }

    @FXML
    public void removeCartItem(){
        CashierProduct selectedProduct = tblCart.getSelectionModel().getSelectedItem();
        if(selectedProduct != null){
            tblCart.getItems().remove(selectedProduct);
            tblCart.refresh();
        }
        else{
            showAlert("Cart","Product Selection","Please Select a Product First");
        }
    }

    @FXML
    void searchProducts(ActionEvent event) {
        String searchColumn = cmbVPSearchColumn.getValue();
        String searchValue = tfVPSearchVal.getText();

        if(searchColumn.equals("ID")){
            searchColumn = "productID";
        }
        else if(searchColumn.equals("Name")){
            searchColumn = "productName";
        }
        else if(searchColumn.equals("Category")){
            searchColumn = "category";
        }
        else if(searchColumn.equals("Sale Price")){
            searchColumn = "salePrice";
        }
        else if(searchColumn.equals("Unit Price")){
            searchColumn = "unitPrice";
        }
        else if(searchColumn.equals("Carton Price")){
            searchColumn = "cartonPrice";
        }
        else if(searchColumn.equals("Quantity")) {
            searchColumn = "quantity";
        }

        try {
            if (searchColumn != null && !searchValue.isEmpty()) {
                tblProducts.setItems(cashierModel.searchProducts(branchID, searchColumn, searchValue));
            } else {
                // Reset to show all products if search field is empty
                tblProducts.setItems(cashierModel.getAllProducts(branchID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
