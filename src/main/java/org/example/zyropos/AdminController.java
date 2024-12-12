package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import database.model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController extends DashboardController implements Initializable {

    private AdminModel adminModel;
    private String username;
    private int branchID;



    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: "+username);

        //Getting BranchID Corresponding to the username
        branchID = adminModel.getBranchID("BranchManager",username);
        System.out.println("Branch ID: "+branchID);
    }

    @FXML
    private Pane emPane;

    @FXML
    private BorderPane cpPane;

    @FXML
    private BorderPane reportPane;

    @FXML
    private BorderPane vdoPane;

    @FXML
    private BorderPane cashierPane;

    @FXML
    private Label lblPerson;

    @FXML
    private JFXButton btnAddEmp;

    @FXML
    private JFXButton btnVR;

    @FXML
    private JFXButton btnCP;

    @FXML
    private JFXButton btnVE;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnVDO;

    @FXML
    private JFXButton btnCa;

    @FXML
    private JFXComboBox<String> cmbCurrency;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private JFXComboBox<String> cmbReportType;
    @FXML
    private JFXComboBox<String> cmbRange;

    @FXML
    private JFXComboBox<String> cmbSearchCol;

    @FXML
    private JFXComboBox<String> cmbSearchCol2;

    @FXML
    private Pane innerPane1;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private HBox rootScene;

    @FXML
    private AnchorPane sideAnchorPane;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfBID;

    @FXML
    private TextField tfContact;

    @FXML
    private TextField tfEID;

    @FXML
    private TextField tfEName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfSalary;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfSearchVal1;
    @FXML
    private TextField tfSearchVal;

    //Data Operator Table View

    @FXML
    private TableView<DataOperator> tblDataOperators;

    @FXML
    private TableColumn<DataOperator, Integer> doID;
    @FXML
    private TableColumn<DataOperator, String> doName;
    @FXML
    private TableColumn<DataOperator, Integer> doBranchID;
    @FXML
    private TableColumn<DataOperator, String> doContact;
    @FXML
    private TableColumn<DataOperator, String> doAddress;
    @FXML
    private TableColumn<DataOperator, String> doEmail;
    @FXML
    private TableColumn<DataOperator, ?> doSalary;

    //Cashier Table View

    @FXML
    private TableView<Cashier> tblCashiers;

    @FXML
    private TableColumn<Cashier, Integer> cID;
    @FXML
    private TableColumn<Cashier, String> cName;
    @FXML
    private TableColumn<Cashier, Integer> cBranchID;
    @FXML
    private TableColumn<Cashier, String> cContact;
    @FXML
    private TableColumn<Cashier, String> cAddress;
    @FXML
    private TableColumn<Cashier, String> cEmail;
    @FXML
    private TableColumn<Cashier, String> cSalary;

    private String[] role={"Data Entry Operator","Cashier"};
    private String[] currency={"PKR","USD","GBP"};
    private String[] reportType={"Sales","Remaining Stock","Profit"};
    private String[] range={"Daily","Weekly","Monthly","Yearly"};
    private String[] searchCol={"ID","Name","Branch ID","Contact","Address","Email","Salary"};

    public AdminController() {
        adminModel=new AdminModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //lblPerson.setText("Welcome "+ Values.PERSON_NAME);
        //lblPerson.setAlignment(Pos.CENTER);

        btnAddEmp.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);
        btnCP.setFocusTraversable(false);
        btnVR.setFocusTraversable(false);
        btnVDO.setFocusTraversable(false);
        btnCa.setFocusTraversable(false);

        cmbRole.getItems().addAll(role);
        cmbCurrency.getItems().addAll(currency);
        cmbReportType.getItems().addAll(reportType);
        cmbRange.getItems().addAll(range);
        cmbSearchCol.getItems().addAll(searchCol);
        cmbSearchCol2.getItems().addAll(searchCol);

    }

    private void setupOperatorTable(){
        doID.setCellValueFactory(new PropertyValueFactory<>("id"));
        doName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doBranchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        doContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        doAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        doEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        doSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void setupCashierTable(){
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cBranchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        cContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }


    @FXML
    void displayEM(ActionEvent event) {
        lblPerson.setVisible(false);
        reportPane.setVisible(false);
        cpPane.setVisible(false);
        vdoPane.setVisible(false);
        cashierPane.setVisible(false);
        emPane.setVisible(true);
    }

    @FXML
    void displayVR(){
        lblPerson.setVisible(false);
        cpPane.setVisible(false);
        emPane.setVisible(false);
        vdoPane.setVisible(false);
        cashierPane.setVisible(false);
        reportPane.setVisible(true);
    }

    @FXML
    void displayCP(){
        lblPerson.setVisible(false);
        reportPane.setVisible(false);
        emPane.setVisible(false);
        vdoPane.setVisible(false);
        cashierPane.setVisible(false);
        cpPane.setVisible(true);
    }

    @FXML
    public void displayVDO() throws SQLException {
        lblPerson.setVisible(false);
        reportPane.setVisible(false);
        cpPane.setVisible(false);
        emPane.setVisible(false);
        cashierPane.setVisible(false);
        setupOperatorTable();
        tblDataOperators.setItems(adminModel.getAllOperators(branchID));
        vdoPane.setVisible(true);
    }

    @FXML
    public void displayCashier() throws SQLException {
        lblPerson.setVisible(false);
        reportPane.setVisible(false);
        cpPane.setVisible(false);
        emPane.setVisible(false);
        vdoPane.setVisible(false);
        setupCashierTable();
        tblCashiers.setItems(adminModel.getAllCashiers(branchID));
        cashierPane.setVisible(true);
    }

    @FXML
    void handleLogout() throws IOException {
        logout(btnLogout);
    }

    @FXML
    void submit() throws SQLException {
        adminModel.addNewEmployeeToDatabase(cmbRole.getValue(),Integer.parseInt(tfEID.getText()),tfEName.getText(),Integer.parseInt(tfBID.getText()),tfContact.getText(),tfAddress.getText(),tfEmail.getText(),tfSalary.getText(),tfUsername.getText(),pfPassword.getText());
    }

    @FXML
    public void searchCashiers(){
        String searchColumn = cmbSearchCol2.getValue();
        String searchValue = tfSearchVal1.getText();

        if(searchColumn.equals("ID")){
            searchColumn = "employeeID";
        }
        else if(searchColumn.equals("Name")){
            searchColumn = "employeeName";
        }
        else if(searchColumn.equals("Branch ID")){
            searchColumn = "branchID";
        }
        else if(searchColumn.equals("Contact")){
            searchColumn = "contact";
        }
        else if(searchColumn.equals("Address")){
            searchColumn = "address";
        }
        else if(searchColumn.equals("Email")){
            searchColumn = "email";
        }
        else if(searchColumn.equals("Salary")){
            searchColumn = "salary";
        }

        try {
            if (searchColumn != null && !searchValue.isEmpty()) {
                tblCashiers.setItems(adminModel.searchCashiers(branchID,searchColumn, searchValue));
            } else {
                // Reset to show all products if search field is empty
                tblCashiers.setItems(adminModel.getAllCashiers(branchID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void removeCashiers(){
        if(showAlertConfirmation("Remove Cashier","Are you sure you want to proceed?","The corresponding data will be deleted from database as well.")) {
            Cashier selectedCashier = tblCashiers.getSelectionModel().getSelectedItem();
            if (selectedCashier != null) {
                try {
                    adminModel.removeCashier(selectedCashier.getId());
                    tblCashiers.getItems().remove(selectedCashier);
                    tblCashiers.refresh();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    public void searchDOs(){
        String searchColumn = cmbSearchCol.getValue();
        String searchValue = tfSearchVal.getText();

        if(searchColumn.equals("ID")){
            searchColumn = "employeeID";
        }
        else if(searchColumn.equals("Name")){
            searchColumn = "employeeName";
        }
        else if(searchColumn.equals("Branch ID")){
            searchColumn = "branchID";
        }
        else if(searchColumn.equals("Contact")){
            searchColumn = "contact";
        }
        else if(searchColumn.equals("Address")){
            searchColumn = "address";
        }
        else if(searchColumn.equals("Email")){
            searchColumn = "email";
        }
        else if(searchColumn.equals("Salary")){
            searchColumn = "salary";
        }

        try {
            if (searchColumn != null && !searchValue.isEmpty()) {
                tblDataOperators.setItems(adminModel.searchDOs(branchID,searchColumn, searchValue));
            } else {
                // Reset to show all products if search field is empty
                tblDataOperators.setItems(adminModel.getAllOperators(branchID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void removeDOs() {
        if(showAlertConfirmation("Remove Data Entry Operator","Are you sure you want to proceed?","The corresponding data will be deleted from database as well.")) {
            DataOperator selectedOperator = tblDataOperators.getSelectionModel().getSelectedItem();
            if (selectedOperator != null) {
                try {
                    adminModel.removeDO(selectedOperator.getId());
                    tblCashiers.getItems().remove(selectedOperator);
                    tblCashiers.refresh();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
