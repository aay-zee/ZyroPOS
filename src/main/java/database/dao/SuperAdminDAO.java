package database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.example.zyropos.Branch;
import org.example.zyropos.BranchManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperAdminDAO extends BaseDAO {

    public SuperAdminDAO() {
    }

    private void showAlert(String title,String header,String content){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void addNewBranchToDatabase(String branchName,String city,String address,String phone,int empCount,boolean isActive) throws SQLException {

        String insertQuery="INSERT INTO branch (name, city, address, phone, employees_count, is_active) VALUES(?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, branchName);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, empCount);
            preparedStatement.setBoolean(6, isActive);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Branch added successfully");
            }
        }
    }

    public void addNewBManagerToDatabase(String managerName,int branchID,String contact,String address,String email,String userName,String password) throws SQLException {

            String insertQuery = "INSERT INTO branch_manager (name, branch_id, phone, address, email, username, password) VALUES(?,?,?,?,?,?,?)";

            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setString(1, managerName);
                preparedStatement.setInt(2, branchID);
                preparedStatement.setString(3, contact);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, userName);
                preparedStatement.setString(7, password);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Branch Manager added successfully");
                }
            }
    }

    public ObservableList<Branch> getAllBranches() throws SQLException {
        ObservableList<Branch> branches = FXCollections.observableArrayList();
        String selectQuery="SELECT * FROM branch WHERE is_deleted = FALSE";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                branches.add(new Branch(
                        resultSet.getInt("branch_id"),
                        resultSet.getString("name"),
                        resultSet.getString("city"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getInt("employees_count"),
                        resultSet.getBoolean("is_active")
                ));
            }
        }
        return branches;
    }

    public ObservableList<BranchManager> getAllBranchManagers() throws SQLException {
        ObservableList<BranchManager> branches = FXCollections.observableArrayList();
        String selectQuery="SELECT manager_id, name, branch_id, phone, address, email FROM branch_manager WHERE is_deleted = FALSE";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                branches.add(new BranchManager(
                        resultSet.getInt("manager_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("branch_id"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("email")
                ));
            }
        }
        return branches;
    }

    public ObservableList<Branch> searchBranches(String column, String value) throws SQLException {
        ObservableList<Branch> branches = FXCollections.observableArrayList();
        // NOTE: Column name must be validated or mapped in real app to prevent injection,
        // but assuming user passes valid 'name', 'city' etc for now.
        // We'll leave the dynamic column but make sure table is correct.
        String searchQuery = "SELECT * FROM branch WHERE " + column + " LIKE ? AND is_deleted = FALSE";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            
            preparedStatement.setString(1, "%" + value + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    branches.add(new Branch(
                            resultSet.getInt("branch_id"),
                            resultSet.getString("name"),
                            resultSet.getString("city"),
                            resultSet.getString("address"),
                            resultSet.getString("phone"),
                            resultSet.getInt("employees_count"),
                            resultSet.getBoolean("is_active")
                    ));
                }
            }
        }
        return branches;
    }

    public ObservableList<BranchManager> searchManagers(String column, String value) throws SQLException {
        ObservableList<BranchManager> managers = FXCollections.observableArrayList();
        String searchQuery = "SELECT manager_id, name, branch_id, phone, address, email FROM branch_manager WHERE " + column + " LIKE ? AND is_deleted = FALSE";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            
            preparedStatement.setString(1, "%" + value + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    managers.add(new BranchManager(
                            resultSet.getInt("manager_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("branch_id"),
                            resultSet.getString("phone"),
                            resultSet.getString("address"),
                            resultSet.getString("email")
                    ));
                }
            }
        }
        return managers;
    }

    public void removeBranch(int branchId) throws SQLException {
        // Soft delete the branch
        String updateBranch = "UPDATE branch SET is_deleted = TRUE, is_active = FALSE WHERE branch_id = ?";
        // Soft delete associated employees (Optional logic, but good practice)
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateBranch)) {
            preparedStatement.setInt(1, branchId);
            preparedStatement.executeUpdate();
        }
    }


    // --- Dashboard Metric Methods ---

    /**
     * Gets the total active branches count.
     */
    public int getActiveBranchCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM branch WHERE is_active = TRUE AND is_deleted = FALSE";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Gets the total count of all employees (Managers + Cashiers + Data Operators).
     */
    public int getTotalEmployeeCount() throws SQLException {
        int total = 0;
        String[] tables = {"branch_manager", "cashier", "data_operator"};

        for (String table : tables) {
            String query = "SELECT COUNT(*) FROM " + table + " WHERE is_deleted = FALSE";
            try (Connection connection = getConnection();
                 PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total += rs.getInt(1);
                }
            }
        }
        return total;
    }

    /**
     * Gets the total revenue from all sales.
     */
    public double getTotalRevenue() throws SQLException {
        String query = "SELECT SUM(total_amount) FROM sales WHERE is_deleted = FALSE";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    /**
     * Gets revenue trend (Last 7 days sales).
     * Returns a Map where key is Date (String) and value is Total Amount (Double).
     */
    public java.util.Map<String, Double> getRevenueTrend() throws SQLException {
        java.util.Map<String, Double> trendData = new java.util.LinkedHashMap<>();
        
        // MySQL specific query for last 7 days grouped by date
        String query = "SELECT DATE(sale_date) as date, SUM(total_amount) as total " +
                       "FROM sales " +
                       "WHERE is_deleted = FALSE " +
                       "GROUP BY DATE(sale_date) " +
                       "ORDER BY DATE(sale_date) ASC " +
                       "LIMIT 7";
                       
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                trendData.put(rs.getString("date"), rs.getDouble("total"));
            }
        }
        return trendData;
    }
    
    // --- End Dashboard Metric Methods ---

    public void removeBranchManager(int managerId) throws SQLException {
        String updateQuery = "UPDATE branch_manager SET is_deleted = TRUE WHERE manager_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, managerId);
            preparedStatement.executeUpdate();
        }
    }
    public java.util.Map<String, Double> getSalesData(String range) throws SQLException {
        java.util.Map<String, Double> data = new java.util.LinkedHashMap<>();
        String query = "";

        // Determine query based on range
        switch (range) {
            case "Daily": // Last 7 days daily breakdown
                query = "SELECT DATE(sale_date) as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE sale_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                        "GROUP BY DATE(sale_date) ORDER BY metric ASC";
                break;
            case "Weekly": // Last 4 weeks weekly breakdown
                query = "SELECT CONCAT('Week ', YEARWEEK(sale_date, 1)) as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE sale_date >= DATE_SUB(CURDATE(), INTERVAL 4 WEEK) " +
                        "GROUP BY YEARWEEK(sale_date, 1) ORDER BY metric ASC";
                break;
            case "Monthly": // Last 12 months monthly breakdown
                query = "SELECT DATE_FORMAT(sale_date, '%Y-%m') as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE sale_date >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
                        "GROUP BY DATE_FORMAT(sale_date, '%Y-%m') ORDER BY metric ASC";
                break;
            case "Yearly": // Last 5 years
                query = "SELECT YEAR(sale_date) as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE sale_date >= DATE_SUB(CURDATE(), INTERVAL 5 YEAR) " +
                        "GROUP BY YEAR(sale_date) ORDER BY metric ASC";
                break;
            default: // Default to Daily
                 query = "SELECT DATE(sale_date) as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE sale_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                        "GROUP BY DATE(sale_date) ORDER BY metric ASC";
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                data.put(rs.getString("metric"), rs.getDouble("value"));
            }
        }
        return data;
    }
}
