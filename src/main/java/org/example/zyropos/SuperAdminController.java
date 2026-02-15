package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import utilities.PasswordUtil;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    // Add Branch Pane
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
    private JFXRadioButton rdABActive;
    @FXML
    private JFXRadioButton rdABUnactive;

    // Add Branch Manager Pane
    @FXML
    private TextField tfMID;
    @FXML
    private TextField tfMName;
    @FXML
    private JFXComboBox<String> cbBranch;
    @FXML
    private TextField tfContact;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfPasswordVisible;
    @FXML
    private JFXButton btnTogglePassword;
    @FXML
    private ImageView ivPasswordToggle;

    @FXML
    private TextField tfVMSearchVal;

    @FXML
    private TextField tfSearchVal;

    // View Reports
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

    // View Branch Table
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

    // View Manager Table
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

    private String[] reportType = { "Sales", "Remaining Stock", "Profit" };
    private String[] range = { "Daily", "Weekly", "Monthly", "Yearly" };
    private String[] branchCols = { "Branch ID", "Name", "City", "Address", "Contact", "Employee Count", "Status" };
    private String[] managerCols = { "Manager ID", "Name", "Branch ID", "Contact", "Address", "Email" };

    @FXML
    private JFXButton btnDashboard;

    // Dashboard Elements
    @FXML
    private VBox dashboardPane;
    @FXML
    private Label lblTotalRevenue;
    @FXML
    private Label lblActiveBranches;
    @FXML
    private Label lblTotalEmployees;
    @FXML
    private LineChart<String, Number> dashboardChart;

    // --- Initialization ---

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String welcomeText = "Welcome, Super Admin";
        if (Values.PERSON_NAME != null && !Values.PERSON_NAME.isEmpty()) {
            welcomeText = "Welcome, " + Values.PERSON_NAME;
        }
        lblPerson.setText(welcomeText);

        ObservableList<Branch> branches;
        try {
            branches = saDAO.getAllBranches();
            ObservableList<String> branchForCombo = FXCollections.observableArrayList();
            for (Branch b : branches) {
                branchForCombo.add(b.getBranchID() + " - " + b.getBranchName());
            }
            cbBranch.setItems(branchForCombo);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Setup Charts and Tables
        setupDashboard();

        // Default View
        showDashboard();

        // Initializing combos
        cbReportType.getItems().addAll(reportType);
        cbRange.getItems().addAll(range);
        cmbSearchColumn.getItems().addAll(branchCols);
        cmbVMSearchCol.getItems().addAll(managerCols);

        // Bind password fields
        tfPasswordVisible.managedProperty().bind(tfPasswordVisible.visibleProperty());
        pfPassword.managedProperty().bind(pfPassword.visibleProperty());
        tfPasswordVisible.textProperty().bindBidirectional(pfPassword.textProperty());
    }

    private void setupDashboard() {
        try {
            // Fetch Metrics
            double revenue = saDAO.getTotalRevenue();
            int activeBranches = saDAO.getActiveBranchCount();
            int totalEmployees = saDAO.getTotalEmployeeCount();

            // Update Labels
            lblTotalRevenue.setText(String.format("$%.2f", revenue));
            lblActiveBranches.setText(String.valueOf(activeBranches));
            lblTotalEmployees.setText(String.valueOf(totalEmployees));

            // Populate Chart with Real Data
            java.util.Map<String, Double> trend = saDAO.getRevenueTrend();
            XYChart.Series series = new XYChart.Series();
            series.setName("Revenue");

            if (trend.isEmpty()) {
                // If no data, show empty or placeholder to avoid blank chart confusion
                // For now, let's just leave it empty or add a "No Data" point
                // series.getData().add(new XYChart.Data("No Data", 0));
            } else {
                for (java.util.Map.Entry<String, Double> entry : trend.entrySet()) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                }
            }

            dashboardChart.getData().clear();
            dashboardChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Dashboard Load Failed", "Could not load dashboard metrics.");
        }
    }

    public SuperAdminController() {
        saDAO = new SuperAdminDAO();
    }

    // --- Navigation Handlers ---

    private void hideAllPanes() {
        dashboardPane.setVisible(false);
        branchPane.setVisible(false);
        bmPane.setVisible(false);
        reportPane.setVisible(false);
        vbPane.setVisible(false);
        vmPane.setVisible(false);
    }

    @FXML
    public void showDashboard() {
        hideAllPanes();
        dashboardPane.setVisible(true);
        // Refresh metrics if needed
        setupDashboard();
    }

    public void handleLogout() throws IOException {
        logout(btnLogout);
    }

    public void addBranch() {
        hideAllPanes();
        branchPane.setVisible(true);

        ToggleGroup status = new ToggleGroup();
        rdABActive.setToggleGroup(status);
        rdABUnactive.setToggleGroup(status);
    }

    private void setupBranchTable() {
        branchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        name.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        contact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        empCount.setCellValueFactory(new PropertyValueFactory<>("empCount"));

        status.setCellValueFactory(new PropertyValueFactory<>("active"));
        status.setCellFactory(column -> new TableCell<Branch, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item ? "Active" : "Inactive");
                    if (item) {
                        setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;"); // Green for Active
                    } else {
                        setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;"); // Red for Inactive
                    }
                }
            }
        });
    }

    private void setupManagerTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("managerID"));
        managerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerBranchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        managerContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        managerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        managerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    public void viewBranch() throws SQLException {
        hideAllPanes();
        vbPane.setVisible(true);
        setupBranchTable();
        tblBranches.setItems(saDAO.getAllBranches());
    }

    @FXML
    public void displayManagers() throws SQLException {
        hideAllPanes();
        vmPane.setVisible(true);
        setupManagerTable();
        tblManagers.setItems(saDAO.getAllBranchManagers());
    }

    public void addBranchDB() throws SQLException {
        // --- Input Validation ---

        // 1. Check Empty Fields
        if (tfABBranchName.getText().trim().isEmpty() ||
                tfABCity.getText().trim().isEmpty() ||
                tfABAddress.getText().trim().isEmpty() ||
                tfABContact.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Missing Required Fields",
                    "Please fill in all fields (Name, City, Address, Contact).");
            return;
        }

        // 2. Validate Contact Number (Digits only, length check basic)
        String contact = tfABContact.getText().trim();
        if (!contact.matches("\\d+")) {
            showAlert("Validation Error", "Invalid Contact Number", "Contact number must contain only digits.");
            return;
        }
        if (contact.length() < 10 || contact.length() > 15) { // Basic length sanity check
            showAlert("Validation Error", "Invalid Contact Number",
                    "Contact number length appears incorrect (expecting 10-15 digits).");
            return;
        }

        // --- Logic ---

        boolean status = true;
        if (rdABUnactive.isSelected()) {
            status = false;
        }

        try {
            // Employee count is always 0 for new branches initially, or calculated
            // dynamically from other tables.
            // We removed the input field, so we pass 0.
            int emp = 0;

            saDAO.addNewBranchToDatabase(
                    tfABBranchName.getText().trim(),
                    tfABCity.getText().trim(),
                    tfABAddress.getText().trim(),
                    tfABContact.getText().trim(),
                    emp,
                    status);

            showAlert("Success", "Branch Created", "New branch added successfully!");
            clearAddBranchForm();

            // Refresh dashboard (optional but good)
            setupDashboard();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error", "Failed to add branch: " + e.getMessage());
        }
    }

    @FXML
    public void clearAddBranchForm() {
        tfABBranchName.clear();
        tfABCity.clear();
        tfABAddress.clear();
        tfABContact.clear();
        rdABActive.setSelected(true);
    }

    public void addBM() {
        hideAllPanes();
        bmPane.setVisible(true);
    }

    public void addBManagerDB() throws SQLException {
        // --- Validation ---
        if (tfMName.getText().trim().isEmpty() ||
                tfUsername.getText().trim().isEmpty() ||
                pfPassword.getText().trim().isEmpty() ||
                cbBranch.getValue() == null ||
                tfContact.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Missing Required Fields",
                    "Please fill in all required fields (Name, Branch ID, Contact, Username, Password).");
            return;
        }

        // Validate Branch ID (Internal ID check)
        String branchSelection = cbBranch.getValue().split(" - ")[0];

        // Validate Contact (Digits only)
        String contact = tfContact.getText().trim();
        if (!contact.matches("\\d+") || contact.length() < 10) {
            showAlert("Validation Error", "Invalid Contact", "Contact number must be valid digits.");
            return;
        }

        try {
            int bid = Integer.parseInt(branchSelection);

            // Note: We could verify if Branch Exists here via DAO, but Foreign Key
            // constraint is a safety net (though ugly error).
            // For now, let's rely on DB constraint or add DAO check later.

            saDAO.addNewBManagerToDatabase(
                    tfMName.getText().trim(),
                    bid,
                    tfContact.getText().trim(),
                    tfAddress.getText().trim(),
                    tfEmail.getText().trim(),
                    tfUsername.getText().trim(),
                    PasswordUtil.hashPassword(pfPassword.getText().trim()) // DAO hashes this now
            );

            showAlert("Success", "Manager Created", "Branch Manager added successfully!");
            clearAddManagerForm();
            setupDashboard(); // Update Employee Count metric

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("foreign key")) {
                showAlert("Error", "Invalid Branch ID", "No branch exists with ID: " + branchSelection);
            } else if (e.getMessage().contains("Duplicate entry")) {
                showAlert("Error", "Duplicate User", "Username already exists.");
            } else {
                showAlert("Error", "Database Error", "Failed to add manager: " + e.getMessage());
            }
        }
    }

    @FXML
    public void clearAddManagerForm() {
        tfMName.clear();
        cbBranch.getSelectionModel().clearSelection();
        tfContact.clear();
        tfAddress.clear();
        tfEmail.clear();
        tfUsername.clear();
        pfPassword.clear();
    }

    @FXML
    private void togglePasswordVisibility() {
        if (tfPasswordVisible.isVisible()) {
            tfPasswordVisible.setVisible(false);
            pfPassword.setVisible(true);
            ivPasswordToggle.setOpacity(1.0); // Full opacity for "view" icon when hidden (click to view)
        } else {
            tfPasswordVisible.setVisible(true);
            pfPassword.setVisible(false);
            ivPasswordToggle.setOpacity(0.5); // Dimmed opacity when password is visible
        }
    }

    public void viewReport() {
        hideAllPanes();
        reportPane.setVisible(true);
    }

    @FXML
    public void searchManagers() {
        String searchColumn = cmbVMSearchCol.getValue();
        String searchValue = tfVMSearchVal.getText();

        if (searchColumn == null)
            return;

        if (searchColumn.equals("ID")) {
            searchColumn = "manager_id";
        } else if (searchColumn.equals("Name")) {
            searchColumn = "name";
        } else if (searchColumn.equals("Branch ID")) {
            searchColumn = "branch_id";
        } else if (searchColumn.equals("Contact")) {
            searchColumn = "phone";
        } else if (searchColumn.equals("Address")) {
            searchColumn = "address";
        } else if (searchColumn.equals("Email")) {
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
            e.printStackTrace(); // Log error
        }
    }

    @FXML
    public void searchBranches() {
        String searchColumn = cmbSearchColumn.getValue();
        String searchValue = tfSearchVal.getText();

        if (searchColumn == null)
            return;

        if (searchColumn.equals("Branch ID")) {
            searchColumn = "branch_id";
        } else if (searchColumn.equals("Name")) {
            searchColumn = "name";
        } else if (searchColumn.equals("City")) {
            searchColumn = "city"; // Fixed space
        } else if (searchColumn.equals("Address")) {
            searchColumn = "address";
        } else if (searchColumn.equals("Contact")) {
            searchColumn = "phone";
        } else if (searchColumn.equals("Employee Count")) {
            searchColumn = "employees_count";
        } else if (searchColumn.equals("Status")) {
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
            e.printStackTrace();
        }
    }

    @FXML
    public void removeBranches() {
        Branch selectedBranch = tblBranches.getSelectionModel().getSelectedItem();
        if (selectedBranch == null) {
            showAlert("Selection Error", "No Branch Selected", "Please select a branch to remove.");
            return;
        }

        if (showAlertConfirmation("Remove Branch", "Are you sure you want to proceed?",
                "The corresponding data will be deleted from database as well.")) {
            try {
                saDAO.removeBranch(selectedBranch.getBranchID());
                // Refresh from DB instead of just removing list item to ensure sync
                tblBranches.setItems(saDAO.getAllBranches());
                // Update dashboard metrics since data changed
                setupDashboard();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void removeBManagers() {
        BranchManager selectedBManager = tblManagers.getSelectionModel().getSelectedItem();
        if (selectedBManager == null) {
            showAlert("Selection Error", "No Manager Selected", "Please select a manager to remove.");
            return;
        }

        if (showAlertConfirmation("Remove Branch Manager", "Are you sure you want to proceed?",
                "The corresponding data will be deleted from database as well.")) {
            try {
                saDAO.removeBranchManager(selectedBManager.getManagerID());
                // Refresh from DB
                tblManagers.setItems(saDAO.getAllBranchManagers());
                // Update dashboard metrics
                setupDashboard();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void setupChart() {
        String type = cbReportType.getValue();
        String range = cbRange.getValue();

        if (type == null)
            type = "Sales";
        if (range == null)
            range = "Daily";

        lineChart.getData().clear();
        lineChart.setTitle(type + " Overview (" + range + ")");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(type);

        try {
            // For now, we only implemented Sales data in DAO.
            // Future: Switch on 'type' to call different DAO methods (e.g., getStickData,
            // getProfitData)
            java.util.Map<String, Double> data = saDAO.getSalesData(range);

            for (java.util.Map.Entry<String, Double> entry : data.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            if (data.isEmpty()) {
                series.getData().add(new XYChart.Data<>("No Data", 0));
            }

            lineChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Report Generation Failed", "Could not fetch data: " + e.getMessage());
        }
    }
}
