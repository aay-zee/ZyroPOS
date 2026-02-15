package database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.zyropos.Cashier;
import org.example.zyropos.DataOperator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO extends BaseDAO {

    public AdminDAO() {
    }

    public void addNewEmployeeToDatabase(String role, String eName, int bID, String contact, String address, String email, String salary, String username, String password) throws SQLException {
        // Map roles to table names
        String tableName = switch (role) {
            case "Cashier" -> "cashier";
            case "Data Entry Operator" -> "data_operator"; // Matched combobox value
            default -> throw new SQLException("Invalid role: " + role);
        };

        // Removed employeeID since it should be AUTO_INCREMENT in DB
        String insertQuery="INSERT INTO "+tableName+" (name, branch_id, contact, address, email, salary, username, password) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            String hashedPassword = utilities.PasswordUtil.hashPassword(password);

            preparedStatement.setString(1, eName);
            preparedStatement.setInt(2, bID);
            preparedStatement.setString(3, contact);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, salary);
            preparedStatement.setString(7, username);
            preparedStatement.setString(8, hashedPassword);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Employee added to the database");
            }
        }
    }

    public ObservableList<DataOperator> getAllOperators(int branchID) throws SQLException {
        ObservableList<DataOperator> operators = FXCollections.observableArrayList();
        String selectQuery="SELECT operator_id, name, branch_id, contact, address, email, salary FROM data_operator WHERE branch_id=? AND is_deleted = FALSE";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            
            preparedStatement.setInt(1, branchID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    operators.add(new DataOperator(
                            resultSet.getInt("operator_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("branch_id"),
                            resultSet.getString("contact"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("salary")
                    ));
                }
            }
        }
        return operators;
    }

    public ObservableList<Cashier> getAllCashiers(int brancID) throws SQLException {
        ObservableList<Cashier> cashiers = FXCollections.observableArrayList();
        String selectQuery="SELECT cashier_id, name, branch_id, contact, address, email, salary FROM cashier WHERE branch_id=? AND is_deleted = FALSE";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            
            preparedStatement.setInt(1, brancID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cashiers.add(new Cashier(
                            resultSet.getInt("cashier_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("branch_id"),
                            resultSet.getString("contact"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("salary")
                    ));
                }
            }
        }
        return cashiers;
    }
    
    public void removeCashier(int id) throws SQLException {
        String query = "UPDATE cashier SET is_deleted = TRUE WHERE cashier_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    public void removeDO(int id) throws SQLException {
         String query = "UPDATE data_operator SET is_deleted = TRUE WHERE operator_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }
    
    // --- Metrics for Dashboard ---
    
    public int getActiveCashierCount(int branchId) throws SQLException {
        String query = "SELECT COUNT(*) FROM cashier WHERE branch_id = ? AND is_deleted = FALSE";
        try (Connection conn = getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, branchId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getActiveDOCount(int branchId) throws SQLException {
         String query = "SELECT COUNT(*) FROM data_operator WHERE branch_id = ? AND is_deleted = FALSE";
        try (Connection conn = getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, branchId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public double getTodayRevenue(int branchId) throws SQLException {
        String query = "SELECT SUM(total_amount) FROM sales WHERE branch_id = ? AND DATE(sale_date) = CURDATE()";
         try (Connection conn = getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, branchId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    public java.util.Map<String, Double> getBranchRevenueTrend(int branchId) throws SQLException {
         java.util.Map<String, Double> data = new java.util.LinkedHashMap<>();
         String query = "SELECT DATE(sale_date) as day, SUM(total_amount) as total FROM sales " +
                        "WHERE branch_id = ? AND sale_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                        "GROUP BY DATE(sale_date) ORDER BY day ASC";
         try (Connection conn = getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
             pst.setInt(1, branchId);
             try (ResultSet rs = pst.executeQuery()) {
                 while (rs.next()) {
                     data.put(rs.getString("day"), rs.getDouble("total"));
                 }
             }
         }
         return data;
    }


    public ObservableList<Cashier> searchCashiers(int branchID,String column, String value) throws SQLException {
        ObservableList<Cashier> cashiers = FXCollections.observableArrayList();
        String searchQuery = "SELECT cashier_id, name, branch_id, contact, address, email, salary FROM cashier WHERE branch_id = ? AND is_deleted = FALSE AND " + column + " LIKE ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            
            preparedStatement.setInt(1, branchID);
            preparedStatement.setString(2, "%" + value + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cashiers.add(new Cashier(
                            resultSet.getInt("cashier_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("branch_id"),
                            resultSet.getString("contact"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("salary")
                    ));
                }
            }
        }
        return cashiers;
    }



    public ObservableList<DataOperator> searchDOs(int branchID,String column, String value) throws SQLException {
        ObservableList<DataOperator> operators = FXCollections.observableArrayList();
        String searchQuery = "SELECT operator_id, name, branch_id, contact, address, email, salary FROM data_operator WHERE branch_id = ? AND is_deleted = FALSE AND " + column + " LIKE ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            
            preparedStatement.setInt(1, branchID);
            preparedStatement.setString(2, "%" + value + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    operators.add(new DataOperator(
                            resultSet.getInt("operator_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("branch_id"),
                            resultSet.getString("contact"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("salary")
                    ));
                }
            }
        }
        return operators;
    }

    public java.util.Map<String, Double> getSalesData(int branchId, String range) throws SQLException {
        java.util.Map<String, Double> data = new java.util.LinkedHashMap<>();
        String query = "";

        // Determine query based on range
        switch (range) {
            case "Daily": // Last 7 days daily breakdown
                query = "SELECT DATE(sale_date) as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE branch_id = ? AND sale_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                        "GROUP BY DATE(sale_date) ORDER BY metric ASC";
                break;
            case "Weekly": // Last 4 weeks weekly breakdown
                query = "SELECT CONCAT('Week ', YEARWEEK(sale_date, 1)) as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE branch_id = ? AND sale_date >= DATE_SUB(CURDATE(), INTERVAL 4 WEEK) " +
                        "GROUP BY YEARWEEK(sale_date, 1) ORDER BY metric ASC";
                break;
            case "Monthly": // Last 12 months monthly breakdown
                query = "SELECT DATE_FORMAT(sale_date, '%Y-%m') as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE branch_id = ? AND sale_date >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
                        "GROUP BY DATE_FORMAT(sale_date, '%Y-%m') ORDER BY metric ASC";
                break;
            case "Yearly": // Last 5 years
                query = "SELECT YEAR(sale_date) as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE branch_id = ? AND sale_date >= DATE_SUB(CURDATE(), INTERVAL 5 YEAR) " +
                        "GROUP BY YEAR(sale_date) ORDER BY metric ASC";
                break;
            default: // Default to Daily
                 query = "SELECT DATE(sale_date) as metric, SUM(total_amount) as value FROM sales " +
                        "WHERE branch_id = ? AND sale_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                        "GROUP BY DATE(sale_date) ORDER BY metric ASC";
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, branchId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    data.put(rs.getString("metric"), rs.getDouble("value"));
                }
            }
        }
        return data;
    }


}
