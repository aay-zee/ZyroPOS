package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import database.dao.SuperAdminDAO;
import utilities.Values;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SuperAdminController extends DashboardController implements Initializable {
    private final SuperAdminDAO saDAO;


    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnAddBranch;
    @FXML
    private JFXButton btnAddBM;
    @FXML
    private JFXButton btnVR;
    @FXML
    private JFXButton btnVB;
    @FXML
    private JFXButton btnVBM;
    @FXML
    private Pane bmPane;

    @FXML
    private BorderPane vbPane;

    @FXML
    private BorderPane vmPane;

    @FXML
    private Label lblPerson;

    @FXML
    private JFXComboBox<String> cmbSearchColumn;
    @FXML
    private JFXComboBox<String> cmbVMSearchCol;

    //Add Branch Pane
    @FXML
    private Pane branchPane;
    @FXML
    private TextField tfABBranchID;
    @FXML
    private TextField tfABBranchName;
    @FXML
    private TextField tfABCity;
    @FXML
    private TextField tfABAddress;
    @FXML
    private TextField tfABContact;
    @FXML
    private TextField tfABEmp;
    @FXML
    private JFXRadioButton rdABActive;
    @FXML
    private JFXRadioButton rdABUnactive;

    //Add Branch Manager Pane
    @FXML
    private TextField tfMID;
    @FXML
    private TextField tfMName;
    @FXML
    private TextField tfBID;
    @FXML
    private TextField tfContact;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfVMSearchVal;

    @FXML
    private TextField tfSearchVal;

    //View Reports
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private BorderPane reportPane;
    @FXML
    private JFXComboBox<String> cbReportType;
    @FXML
    private JFXComboBox<String> cbRange;

    //View Branch Table
    @FXML
    private TableView<Branch> tblBranches;

    @FXML
    private TableColumn<Branch, Integer> branchID;
    @FXML
    private TableColumn<Branch, String> name;
    @FXML
    private TableColumn<Branch, String> city;
    @FXML
    private TableColumn<Branch, String> address;
    @FXML
    private TableColumn<Branch, String> contact;
    @FXML
    private TableColumn<Branch, Integer> empCount;
    @FXML
    private TableColumn<Branch, Boolean> status;

    //View Manager Table
    @FXML
    private TableView<BranchManager> tblManagers;

    @FXML
    private TableColumn<BranchManager, Integer> id;
    @FXML
    private TableColumn<BranchManager, String> managerName;
    @FXML
    private TableColumn<BranchManager, Integer> managerBranchID;
    @FXML
    private TableColumn<BranchManager, String> managerContact;
    @FXML
    private TableColumn<BranchManager, String> managerAddress;
    @FXML
    private TableColumn<BranchManager, String> managerEmail;


    private String[] reportType={"Sales","Remaining Stock","Profit"};
    private String[] range={"Daily","Weekly","Monthly","Yearly"};
    private String[] cols={"Branch ID","Name","City","Address","Contact","Employee Count","Status"};




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblPerson.setText("Welcome "+ Values.PERSON_NAME);
        lblPerson.setAlignment(Pos.CENTER);

        btnAddBranch.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);
        btnAddBM.setFocusTraversable(false);
        btnVR.setFocusTraversable(false);
        btnVB.setFocusTraversable(false);
        btnVBM.setFocusTraversable(false);

        cbReportType.getItems().addAll(reportType);
        cbRange.getItems().addAll(range);
        cmbSearchColumn.getItems().addAll(cols);
        cmbVMSearchCol.getItems().addAll(cols);
        //lineChart= new LineChart<>(x, y);

        XYChart.Series series=new XYChart.Series();
        series.getData().add(new XYChart.Data("1",23));
        series.getData().add(new XYChart.Data("2",45));
        series.getData().add(new XYChart.Data("3",47));
        series.getData().add(new XYChart.Data("4",54));
        series.getData().add(new XYChart.Data("5",34));
        series.getData().add(new XYChart.Data("6",98));
        series.getData().add(new XYChart.Data("7",78));
        series.getData().add(new XYChart.Data("8",56));
        series.getData().add(new XYChart.Data("9",78));
        series.getData().add(new XYChart.Data("10",98));

        XYChart.Series series1=new XYChart.Series();
        series1.getData().add(new XYChart.Data("1",32));
        series1.getData().add(new XYChart.Data("2",23));
        series1.getData().add(new XYChart.Data("3",54));
        series1.getData().add(new XYChart.Data("4",43));
        series1.getData().add(new XYChart.Data("5",66));
        series1.getData().add(new XYChart.Data("6",98));
        series1.getData().add(new XYChart.Data("7",13));
        series1.getData().add(new XYChart.Data("8",56));
        series1.getData().add(new XYChart.Data("9",87));
        series1.getData().add(new XYChart.Data("10",54));
        series1.getData().add(new XYChart.Data("11",33));

        lineChart.getData().add(series);
        lineChart.getData().add(series1);


    }

//    @FXML
//    private void initialize() throws SQLException {
//        btnAddBranch.setFocusTraversable(false);
//        btnLogout.setFocusTraversable(false);
//        btnAddBM.setFocusTraversable(false);
//        btnVR.setFocusTraversable(false);
//
//        XYChart.Series series=new XYChart.Series();
//        series.getData().add(new XYChart.Data("1",23));
//    }



    public SuperAdminController() {
        saDAO=new SuperAdminDAO();
    }

    public void handleLogout() throws IOException {
        logout(btnLogout);
    }

    public void addBranch() {
        lblPerson.setVisible(false);
        ToggleGroup status=new ToggleGroup();
        rdABActive.setToggleGroup(status);
        rdABUnactive.setToggleGroup(status);
        vbPane.setVisible(false);
        bmPane.setVisible(false);
        reportPane.setVisible(false);
        vmPane.setVisible(false);
        branchPane.setVisible(true);
    }

    private void setupBranchTable() {
        branchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        name.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        contact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        empCount.setCellValueFactory(new PropertyValueFactory<>("empCount"));
        status.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    private void setupManagerTable(){
        id.setCellValueFactory(new PropertyValueFactory<>("managerID"));
        managerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerBranchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        managerContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        managerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        managerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    public void viewBranch() throws SQLException {
        lblPerson.setVisible(false);
        bmPane.setVisible(false);
        reportPane.setVisible(false);
        branchPane.setVisible(false);
        vmPane.setVisible(false);
        vbPane.setVisible(true);

//        branchID.setCellValueFactory(new PropertyValueFactory<>("BranchID"));
//        name.setCellValueFactory(new PropertyValueFactory<>("BranchName"));
//        city.setCellValueFactory(new PropertyValueFactory<>("City"));
//        address.setCellValueFactory(new PropertyValueFactory<>("Address"));
//        contact.setCellValueFactory(new PropertyValueFactory<>("Phone"));
//        empCount.setCellValueFactory(new PropertyValueFactory<>("EmpCount"));
//        status.setCellValueFactory(new PropertyValueFactory<>("isActive"));

        setupBranchTable();

        tblBranches.setItems(saDAO.getAllBranches());
    }

    @FXML
    public void displayManagers() throws SQLException {
        lblPerson.setVisible(false);
        bmPane.setVisible(false);
        reportPane.setVisible(false);
        branchPane.setVisible(false);
        vbPane.setVisible(false);
        vmPane.setVisible(true);

        setupManagerTable();
        tblManagers.setItems(saDAO.getAllBranchManagers());
    }

    public void addBranchDB() throws SQLException {
        boolean status=true;
        if(rdABUnactive.isSelected()){
            status=false;
        }
        saDAO.addNewBranchToDatabase(tfABBranchName.getText(),tfABCity.getText(),tfABAddress.getText(),tfABContact.getText(),Integer.parseInt(tfABEmp.getText()),status);

        showAlert("Add Branch","Branch Addition Status","Branch Added Successfully");

        branchPane.getChildren().stream().filter(node -> node instanceof TextField || node instanceof JFXRadioButton).forEach(node -> {
            if(node instanceof TextField){
                ((TextField) node).setText("");
            }
            else{
                ((JFXRadioButton) node).setSelected(false);
            }
        });
    }

    public void addBM() {
        lblPerson.setVisible(false);
        vbPane.setVisible(false);
        reportPane.setVisible(false);
        branchPane.setVisible(false);
        vmPane.setVisible(false);
        bmPane.setVisible(true);
    }

    public void addBManagerDB() throws SQLException {
        saDAO.addNewBManagerToDatabase(tfMName.getText(),Integer.parseInt(tfBID.getText()),tfContact.getText(),tfAddress.getText(),tfEmail.getText(),tfUsername.getText(),tfPassword.getText());

        showAlert("Add Branch Manager","Branch Manager Addition Status","Branch Manager Added Successfully");

        bmPane.getChildren().stream().filter(node -> node instanceof TextField).forEach(node -> {
            ((TextField) node).setText("");
        });
    }

    public void viewReport(){
        lblPerson.setVisible(false);
        vbPane.setVisible(false);
        branchPane.setVisible(false);
        bmPane.setVisible(false);
        vmPane.setVisible(false);
        reportPane.setVisible(true);
    }

    @FXML
    public void searchManagers(){
        String searchColumn = cmbVMSearchCol.getValue();
        String searchValue = tfVMSearchVal.getText();

        if(searchColumn.equals("ID")){
            searchColumn = "manager_id";
        }
        else if(searchColumn.equals("Name")){
            searchColumn = "name";
        }
        else if(searchColumn.equals("Branch ID")){
            searchColumn = "branch_id";
        }
        else if(searchColumn.equals("Contact")){
            searchColumn = "phone";
        }
        else if(searchColumn.equals("Address")){
            searchColumn = "address";
        }
        else if(searchColumn.equals("Email")){
            searchColumn = "email";
        }

        try {
            if (searchColumn != null && !searchValue.isEmpty()) {
                tblManagers.setItems(saDAO.searchManagers(searchColumn, searchValue));
            } else {
                // Reset to show all products if search field is empty
                tblManagers.setItems(saDAO.getAllBranchManagers());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void searchBranches(){
        String searchColumn = cmbSearchColumn.getValue();
        String searchValue = tfSearchVal.getText();

        if(searchColumn.equals("Branch ID")){
            searchColumn = "branch_id";
        }
        else if(searchColumn.equals("Name")){
            searchColumn = "name";
        }
        else if(searchColumn.equals("City")){
            searchColumn = "city    ";
        }
        else if(searchColumn.equals("Address")){
            searchColumn = "address";
        }
        else if(searchColumn.equals("Contact")){
            searchColumn = "phone";
        }
        else if(searchColumn.equals("Employee Count")){
            searchColumn = "employees_count";
        }
        else if(searchColumn.equals("Status")){
            searchColumn = "is_active";
        }

        try {
            if (searchColumn != null && !searchValue.isEmpty()) {
                tblBranches.setItems(saDAO.searchBranches(searchColumn, searchValue));
            } else {
                // Reset to show all products if search field is empty
                tblBranches.setItems(saDAO.getAllBranches());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void removeBranches(){
        if(showAlertConfirmation("Remove Branch","Are you sure you want to proceed?","The corresponding data will be deleted from database as well.")) {
            Branch selectedBranch = tblBranches.getSelectionModel().getSelectedItem();
            if (selectedBranch != null) {
                try {
                    saDAO.removeBranch(selectedBranch.getBranchID());
                    tblBranches.getItems().remove(selectedBranch);
                    tblBranches.refresh();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    public void removeBManagers(){
        if(showAlertConfirmation("Remove Branch Manager","Are you sure you want to proceed?","The corresponding data will be deleted from database as well.")) {
            BranchManager selectedBManager = tblManagers.getSelectionModel().getSelectedItem();
            if (selectedBManager != null) {
                try {
                    saDAO.removeBranchManager(selectedBManager.getManagerID());
                    tblManagers.getItems().remove(selectedBManager);
                    tblManagers.refresh();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
