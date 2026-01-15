package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import database.dao.DataOperatorDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DataOperatorController extends DashboardController implements Initializable {

    private String username;
    private int branchID;
    private DataOperatorDAO dataOperatorDAO;


    public DataOperatorController() {
        dataOperatorDAO = new DataOperatorDAO();
    }

    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: "+username);

        //Getting BranchID Corresponding to the username
        branchID = dataOperatorDAO.getBranchID("DataOperator",username);
        System.out.println("Branch ID: "+branchID);
    }

    @FXML
    private Pane apPane;

    @FXML
    private Pane avPane;

    @FXML
    private JFXButton btnAPSubmit;

    @FXML
    private JFXButton btnAVSubmit;

    @FXML
    private JFXButton btnAddProduct;

    @FXML
    private JFXButton btnAddVendor;

    @FXML
    private JFXButton btnCP;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXToggleButton btnToggle;

    @FXML
    private JFXButton btnVPRemove;

    @FXML
    private JFXButton btnVVRemove;

    @FXML
    private JFXButton btnViewProducts;

    @FXML
    private JFXButton btnViewVendors;

    @FXML
    private JFXComboBox<String> cmbVPSearchColumn;

    @FXML
    private JFXComboBox<String> cmbVVSearchColumn;

    @FXML
    private JFXComboBox<String> cmbVendorID;

    @FXML
    private VBox cpPane;

    @FXML
    private DatePicker dpAVDate;

    @FXML
    private Pane innerPane1;

    @FXML
    private Label lblPerson;

    @FXML
    private BorderPane rootScene;

    @FXML
    private Spinner<Integer> sldAPQuantity;

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TableView<Vendor> tblVendors;

    @FXML
    private TextField tfAPCPrice;

    @FXML
    private TextField tfAPCategory;

    @FXML
    private TextField tfAPName;

    @FXML
    private TextField tfAPOPrice;

    @FXML
    private TextField tfAPSPrice;

    @FXML
    private TextField tfAPUPrice;

    @FXML
    private TextField tfAVCName;

    @FXML
    private TextField tfAVCPerson;

    @FXML
    private TextField tfAVContact;

    @FXML
    private TextField tfAVEmail;

    @FXML
    private TextField tfAVName;

    @FXML
    private TextField tfVPSearchVal;

    @FXML
    private TextField tfVVSearchVal;

    @FXML
    private TableColumn<Product, Double> vpCartonPrice;

    @FXML
    private TableColumn<Product, String> vpCategory;

    @FXML
    private TableColumn<Product, Integer> vpID;

    @FXML
    private TableColumn<Product, String> vpName;

    @FXML
    private TableColumn<Product, Integer> vpVendorID;

    @FXML
    private TableColumn<Product, Integer> vpQuantity;

    @FXML
    private TableColumn<Product, Double> vpOrigPrice;

    @FXML
    private BorderPane vpPane;

    @FXML
    private TableColumn<Product, Double> vpSalePrice;

    @FXML
    private TableColumn<Product, Double> vpUnitPrice;

    @FXML
    private TableColumn<Vendor, String> vvCompName;

    @FXML
    private TableColumn<Vendor, String> vvContact;

    @FXML
    private TableColumn<Vendor, String> vvContactPerson;

    @FXML
    private TableColumn<Vendor, String> vvEmail;

    @FXML
    private TableColumn<Vendor, Integer> vvID;

    @FXML
    private TableColumn<Vendor, String> vvName;

    @FXML
    private BorderPane vvPane;

    @FXML
    private TableColumn<Vendor, String> vvRegDate;

    private String[] vendorCols={"ID","Name","Contact Person","Contact","Email","Company Name","Reg Date"};
    private String[] productCols={"ID","Name","Category","Original Price","Sale Price","Unit Price","Carton Price"};

    private  SpinnerValueFactory<Integer> valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000000);


    @Override
    public void initialize(URL location, ResourceBundle resources) {



        lblPerson.setText(username+"'s Dashboard");
        lblPerson.setAlignment(Pos.CENTER);

        btnAddVendor.setFocusTraversable(false);
        btnAddProduct.setFocusTraversable(false);
        btnViewProducts.setFocusTraversable(false);
        btnViewVendors.setFocusTraversable(false);
        btnCP.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);

        cmbVVSearchColumn.getItems().addAll(vendorCols);
        cmbVPSearchColumn.getItems().addAll(productCols);

        //tfVVSearchVal.textProperty().addListener((observable, oldValue, newValue) -> searchVendors());
    }

    private void setupVendorTable(){
        vvID.setCellValueFactory(new PropertyValueFactory<>("id"));
        vvName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vvContactPerson.setCellValueFactory(new PropertyValueFactory<>("ContactPerson"));
        vvContact.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        vvEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        vvCompName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
        vvRegDate.setCellValueFactory(new PropertyValueFactory<>("RegDate"));
    }

    private void setupProductTable(){
        vpID.setCellValueFactory(new PropertyValueFactory<>("id"));
        vpName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vpVendorID.setCellValueFactory(new PropertyValueFactory<>("VendorID"));
        vpCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        vpOrigPrice.setCellValueFactory(new PropertyValueFactory<>("OriginalPrice"));
        vpSalePrice.setCellValueFactory(new PropertyValueFactory<>("SalePrice"));
        vpUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        vpCartonPrice.setCellValueFactory(new PropertyValueFactory<>("CartonPrice"));
        vpQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    }

    @FXML
    void addProductToDatabase(ActionEvent event) {
        try {
            dataOperatorDAO.addProduct(tfAPName.getText(),Integer.parseInt(cmbVendorID.getValue()),tfAPCategory.getText(),branchID,Double.parseDouble(tfAPOPrice.getText()),Double.parseDouble(tfAPSPrice.getText()),Double.parseDouble(tfAPUPrice.getText()),Double.parseDouble(tfAPCPrice.getText()),Integer.parseInt(sldAPQuantity.getValue().toString()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Refresh Value Containers
        tfAPName.setText("");
        cmbVendorID.setValue(null);
        tfAPCategory.setText("");
        tfAPOPrice.setText("");
        tfAPSPrice.setText("");
        tfAPUPrice.setText("");
        tfAPCPrice.setText("");
        valueFactory.setValue(1);
        sldAPQuantity.setValueFactory(valueFactory);
    }

    @FXML
    void addVendorToDatabase(ActionEvent event) {
        LocalDate selectedDate=dpAVDate.getValue();
        String formattedDate=null;
        if(selectedDate!=null){
            formattedDate=selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        try {
            dataOperatorDAO.addVendor(tfAVName.getText(),branchID,tfAVCPerson.getText(),tfAVContact.getText(),tfAVEmail.getText(),tfAVCName.getText(),formattedDate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Refresh Value Containers
        tfAVName.setText("");
        tfAVCPerson.setText("");
        tfAVContact.setText("");
        tfAVEmail.setText("");
        tfAVCName.setText("");
        dpAVDate.setValue(null);
    }

    @FXML
    void dislayVP(ActionEvent event) {
        lblPerson.setVisible(false);
        avPane.setVisible(false);
        apPane.setVisible(false);
        vvPane.setVisible(false);
        cpPane.setVisible(false);

        setupProductTable();

        try {
            tblProducts.setItems(dataOperatorDAO.getAllProducts(branchID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        vpPane.setVisible(true);

    }

    @FXML
    void displayAV(ActionEvent event) {
        lblPerson.setVisible(false);
        apPane.setVisible(false);
        vvPane.setVisible(false);
        cpPane.setVisible(false);
        vpPane.setVisible(false);
        avPane.setVisible(true);
    }

    @FXML
    void displayCP(ActionEvent event) {
        lblPerson.setVisible(false);
        avPane.setVisible(false);
        apPane.setVisible(false);
        vpPane.setVisible(false);
        vvPane.setVisible(false);
        cpPane.setVisible(true);
    }

    @FXML
    void displayP(ActionEvent event) {
        lblPerson.setVisible(false);
        avPane.setVisible(false);
        vpPane.setVisible(false);
        vvPane.setVisible(false);
        cpPane.setVisible(false);


        valueFactory.setValue(1);
        sldAPQuantity.setValueFactory(valueFactory);

        try {
            cmbVendorID.setItems(dataOperatorDAO.getVendorIDs(branchID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        apPane.setVisible(true);
    }

    @FXML
    void displayVV(ActionEvent event) {
        lblPerson.setVisible(false);
        avPane.setVisible(false);
        vpPane.setVisible(false);
        cpPane.setVisible(false);
        apPane.setVisible(false);
        vvPane.setVisible(true);

        setupVendorTable();
        try {
            tblVendors.setItems(dataOperatorDAO.getAllVendors(branchID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchVendors() {
        String searchColumn = cmbVVSearchColumn.getValue();
        if(searchColumn.equals("Name")){
            searchColumn = "vendorName";
        }
        else if(searchColumn.equals("ID")){
            searchColumn = "vendorID";
        }
        else if(searchColumn.equals("Contact Person")){
            searchColumn = "contactPerson";
        }
        else if(searchColumn.equals("Contact")){
            searchColumn = "phone";
        }
        else if(searchColumn.equals("Email")){
            searchColumn = "email";
        }
        else if(searchColumn.equals("Company Name")){
            searchColumn = "companyName";
        }
        else if(searchColumn.equals("Reg Date")){
            searchColumn = "registrationDate";
        }
        String searchValue = tfVVSearchVal.getText();

        try {
            if (searchColumn != null && !searchValue.isEmpty()) {
                tblVendors.setItems(dataOperatorDAO.searchVendors(branchID, searchColumn, searchValue));
            } else {
                // Reset to show all vendors if search field is empty
                tblVendors.setItems(dataOperatorDAO.getAllVendors(branchID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void removeVendor() {
        if(showAlertConfirmation("Remove Vendor","Are you sure you want to proceed?","The corresponding data will be deleted from database as well.")) {
            Vendor selectedVendor = tblVendors.getSelectionModel().getSelectedItem();
            if (selectedVendor != null) {
                try {
                    dataOperatorDAO.removeVendor(selectedVendor.getId());
                    tblVendors.getItems().remove(selectedVendor);
                    tblVendors.refresh();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    void searchProducts() {
        String searchColumn = cmbVPSearchColumn.getValue();
        String searchValue = tfVPSearchVal.getText();

        if(searchColumn.equals("ID")){
            searchColumn = "productID";
        }
        else if(searchColumn.equals("Name")){
            searchColumn = "productName";
        }
        else if(searchColumn.equals("Vendor ID")){
            searchColumn = "vendorID";
        }
        else if(searchColumn.equals("Category")){
            searchColumn = "category";
        }
        else if(searchColumn.equals("Original Price")){
            searchColumn = "originalPrice";
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
                tblProducts.setItems(dataOperatorDAO.searchProducts(branchID, searchColumn, searchValue));
            } else {
                // Reset to show all products if search field is empty
                tblProducts.setItems(dataOperatorDAO.getAllProducts(branchID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void removeProduct() {
        if(showAlertConfirmation("Remove Product","Are you sure you want to proceed?","The corresponding data will be deleted from database as well.")) {
            Product selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                try {
                    dataOperatorDAO.removeProduct(selectedProduct.getId());
                    tblProducts.getItems().remove(selectedProduct);
                    tblProducts.refresh();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        try {
            logout(btnLogout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

