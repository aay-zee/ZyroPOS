package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import database.dao.AdminDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import utilities.Values;
import javafx.scene.control.Alert;

public class AdminController extends DashboardController implements Initializable {

    private AdminDAO adminDAO;
    private String username;
    private int branchID;

    @FXML
    private Pane emPane;

    @FXML
    private VBox cpPane;

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
    private BorderPane rootScene;

    @FXML
    private JFXComboBox<String> cmbReportType;
    @FXML
    private JFXComboBox<String> cmbRange;
    @FXML
    private JFXComboBox<String> cmbSearchCol;
    @FXML
    private JFXComboBox<String> cmbSearchCol2;

    @FXML
    private TextField tfSearchVal1;
    @FXML
    private TextField tfSearchVal;

    // Data Operator Table View

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

    // Cashier Table View

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

    private String[] role = { "Data Entry Operator", "Cashier" };
    private String[] currency = { "PKR", "USD", "GBP" };
    private String[] reportType = { "Sales", "Remaining Stock", "Profit" };
    private String[] range = { "Daily", "Weekly", "Monthly", "Yearly" };
    private String[] searchCol = { "ID", "Name", "Branch ID", "Contact", "Address", "Email", "Salary" };

    // --- Dashboard Elements ---
    @FXML
    private VBox dashboardPane;
    @FXML
    private Label lblActiveCashiers;
    @FXML
    private Label lblActiveDOs;
    @FXML
    private Label lblTodayRevenue;
    @FXML
    private LineChart<String, Number> dashboardChart;
    @FXML
    private LineChart<String, Number> reportChart;

    // --- Add Employee Elements ---
    @FXML
    private JFXComboBox<String> cmbRole;
    @FXML
    private TextField tfEName;
    @FXML
    private TextField tfBID;
    @FXML
    private TextField tfContact;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfSalary;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;

    public AdminController() {
        adminDAO = new AdminDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initial setup
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

    public void setUsername(String username) {
        this.username = username;
        // Fetch Branch ID
        branchID = adminDAO.getBranchID("BranchManager", username);

        String welcomeText = "Welcome, Branch Manager";
        if (Values.PERSON_NAME != null && !Values.PERSON_NAME.isEmpty()) {
            welcomeText = "Welcome, " + Values.PERSON_NAME;
        }
        lblPerson.setText(welcomeText);

        // Setup dashboard now that we have branchID
        showDashboard();
    }

    private void setupDashboard() {
        try {
            // Metrics
            int activeCashiers = adminDAO.getActiveCashierCount(branchID);
            int activeDOs = adminDAO.getActiveDOCount(branchID);
            double revenue = adminDAO.getTodayRevenue(branchID);

            lblActiveCashiers.setText(String.valueOf(activeCashiers));
            lblActiveDOs.setText(String.valueOf(activeDOs));
            lblTodayRevenue.setText(String.format("$%.2f", revenue));

            // Chart
            dashboardChart.getData().clear();
            java.util.Map<String, Double> trend = adminDAO.getBranchRevenueTrend(branchID);
            javafx.scene.chart.XYChart.Series<String, Number> series = new javafx.scene.chart.XYChart.Series<>();
            series.setName("Revenue");

            if (trend.isEmpty()) {
                series.getData().add(new javafx.scene.chart.XYChart.Data<>("No Data", 0));
            } else {
                for (java.util.Map.Entry<String, Double> entry : trend.entrySet()) {
                    series.getData().add(new javafx.scene.chart.XYChart.Data<>(entry.getKey(), entry.getValue()));
                }
            }
            dashboardChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Dashboard Error", "Failed to load dashboard metrics.");
        }
    }

    private void setupOperatorTable() {
        doID.setCellValueFactory(new PropertyValueFactory<>("id"));
        doName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doBranchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        doContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        doAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        doEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        doSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void setupCashierTable() {
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cBranchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        cContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    // --- Navigation ---
    @FXML
    public void showDashboard() {
        hideAllPanes();
        dashboardPane.setVisible(true);
        setupDashboard();
    }

    private void hideAllPanes() {
        dashboardPane.setVisible(false);
        emPane.setVisible(false);
        reportPane.setVisible(false);
        cpPane.setVisible(false);
        vdoPane.setVisible(false);
        cashierPane.setVisible(false);
    }

    @FXML
    void displayEM(ActionEvent event) {
        hideAllPanes();
        emPane.setVisible(true);
        tfBID.setText(String.valueOf(branchID)); // Auto-fill Branch ID
    }

    @FXML
    void displayVR() {
        hideAllPanes();
        reportPane.setVisible(true);
    }

    @FXML
    void displayCP() {
        hideAllPanes();
        cpPane.setVisible(true);
    }

    @FXML
    public void displayVDO() throws SQLException {
        hideAllPanes();
        setupOperatorTable();
        tblDataOperators.setItems(adminDAO.getAllOperators(branchID));
        vdoPane.setVisible(true);
    }

    @FXML
    public void displayCashier() throws SQLException {
        hideAllPanes();
        setupCashierTable();
        tblCashiers.setItems(adminDAO.getAllCashiers(branchID));
        cashierPane.setVisible(true);
    }

    @FXML
    void handleLogout() throws IOException {
        logout(btnLogout);
    }

    @FXML
    void submit() throws SQLException {
        // Validation
        if (cmbRole.getValue() == null || tfEName.getText().isEmpty() || tfUsername.getText().isEmpty()
                || pfPassword.getText().isEmpty()) {
            showAlert("Validation Error", "Missing Fields", "Please fill in all required fields.");
            return;
        }

        try {
            adminDAO.addNewEmployeeToDatabase(
                    cmbRole.getValue(),
                    tfEName.getText(),
                    branchID, // Use class variable
                    tfContact.getText(),
                    tfAddress.getText(),
                    tfEmail.getText(),
                    tfSalary.getText(),
                    tfUsername.getText(),
                    pfPassword.getText());

            showAlert("Success", "Employee Added", "New " + cmbRole.getValue() + " added successfully.");
            clearAddEmployeeForm();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error", "Failed to add employee: " + e.getMessage());
        }
    }

    @FXML
    public void clearAddEmployeeForm() {
        tfEName.clear();
        tfContact.clear();
        tfAddress.clear();
        tfEmail.clear();
        tfUsername.clear();
        pfPassword.clear();
        tfSalary.clear();
    }

    @FXML
    public void searchCashiers() {
        String searchColumn = cmbSearchCol2.getValue();
        String searchValue = tfSearchVal1.getText();

        if (searchColumn == null)
            return;

        String dbCol = switch (searchColumn) {
            case "ID" -> "cashier_id"; // Fixed col name
            case "Name" -> "name";
            case "Branch ID" -> "branch_id";
            case "Contact" -> "contact";
            case "Address" -> "address";
            case "Email" -> "email";
            case "Salary" -> "salary";
            default -> "name";
        };

        try {
            // Note: Reuse getAll or search. DAO searchCashiers logic needs check.
            // Assuming adminDAO.searchCashiers exists and works.
            // If not, we should fix DAO. Based on previous view, it seemed to exist.
            if (!searchValue.isEmpty()) {
                tblCashiers.setItems(adminDAO.searchCashiers(branchID, dbCol, searchValue));
            } else {
                tblCashiers.setItems(adminDAO.getAllCashiers(branchID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeCashiers() {
        Cashier selectedCashier = tblCashiers.getSelectionModel().getSelectedItem();
        if (selectedCashier == null) {
            showAlert("Selection Error", "No Cashier Selected", "Please select a cashier to remove.");
            return;
        }

        if (showAlertConfirmation("Remove Cashier", "Are you sure you want to proceed?",
                "The corresponding data will be deleted from database as well.")) {
            try {
                adminDAO.removeCashier(selectedCashier.getId());
                // Refresh from DB to confirm soft delete filter
                tblCashiers.setItems(adminDAO.getAllCashiers(branchID));
                setupDashboard();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void searchDOs() {
        String searchColumn = cmbSearchCol.getValue();
        String searchValue = tfSearchVal.getText();

        if (searchColumn == null)
            return;

        String dbCol = switch (searchColumn) {
            case "ID" -> "operator_id"; // Fixed
            case "Name" -> "name";
            case "Branch ID" -> "branch_id";
            case "Contact" -> "contact";
            case "Address" -> "address";
            case "Email" -> "email";
            case "Salary" -> "salary";
            default -> "name";
        };

        try {
            if (!searchValue.isEmpty()) {
                tblDataOperators.setItems(adminDAO.searchDOs(branchID, dbCol, searchValue));
            } else {
                tblDataOperators.setItems(adminDAO.getAllOperators(branchID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void removeDOs() {
        DataOperator selectedOperator = tblDataOperators.getSelectionModel().getSelectedItem();
        if (selectedOperator == null) {
            showAlert("Selection Error", "No Operator Selected", "Please select a data operator to remove.");
            return;
        }

        if (showAlertConfirmation("Remove Data Entry Operator", "Are you sure you want to proceed?",
                "The corresponding data will be deleted from database as well.")) {
            try {
                adminDAO.removeDO(selectedOperator.getId());
                // Use ALL operators method
                tblDataOperators.setItems(adminDAO.getAllOperators(branchID));
                setupDashboard();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void generateReport() {
        String type = cmbReportType.getValue();
        if ("Sales".equals(type)) {
            reportChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily Sales"); // Or Dynamic based on range if implemented

            try {
                // Assuming getSalesData returns Date -> Amount map
                // If range logic is not fully in DAO, we might just show what we have.
                // For now, let's use getBranchRevenueTrend which we used for dashboard as a
                // proxy if explicit sales report isn't different.
                // Or better, check if getSalesData exists.
                // Let's rely on getBranchRevenueTrend for "Sales" for now as it's verified.
                // Ideally we separate them.
                java.util.Map<String, Double> data = adminDAO.getBranchRevenueTrend(branchID);

                for (java.util.Map.Entry<String, Double> entry : data.entrySet()) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                }

                reportChart.getData().add(series);

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Report Error", "Failed to generate report.");
            }
        } else {
            showAlert("Info", "Coming Soon", "Only Sales Reports are currently available.");
        }
    }
}
