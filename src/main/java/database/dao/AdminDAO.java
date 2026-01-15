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

    public void addNewEmployeeToDatabase(String role,int eID,String eName,int bID,String contact,String address,String email,String salary,String username,String password) throws SQLException {
        // Map roles to table names
        String tableName = switch (role) {
            case "Cashier" -> "cashier";
            case "DataOperator" -> "data_operator";
            default -> throw new SQLException("Invalid role: " + role);
        };

        // Note: Using string concatenation for table name has security risks (mitigated by switch above).
        String insertQuery="INSERT INTO "+tableName+" (employeeID, name, branch_id, contact, address, email, salary, username, password) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, eID);
            preparedStatement.setString(2, eName);
            preparedStatement.setInt(3, bID);
            preparedStatement.setString(4, contact);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, salary);
            preparedStatement.setString(8, username);
            preparedStatement.setString(9, password);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Employee added to the database");
            }
        }
    }

    public ObservableList<DataOperator> getAllOperators(int branchID) throws SQLException {
        ObservableList<DataOperator> operators = FXCollections.observableArrayList();
        String selectQuery="SELECT operator_id, name, branch_id, contact, address, email, salary FROM data_operator WHERE branch_id=?";
        
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
        String selectQuery="SELECT cashier_id, name, branch_id, contact, address, email, salary FROM cashier WHERE branch_id=?";
        
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

    public ObservableList<Cashier> searchCashiers(int branchID,String column, String value) throws SQLException {
        ObservableList<Cashier> cashiers = FXCollections.observableArrayList();
        String searchQuery = "SELECT cashier_id, name, branch_id, contact, address, email, salary FROM cashier WHERE branch_id = ? AND " + column + " LIKE ?";
        
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

    public void removeCashier(int cashierId) throws SQLException {
        String deleteQuery = "DELETE FROM cashier WHERE cashier_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, cashierId);
            preparedStatement.executeUpdate();
        }
    }

    public ObservableList<DataOperator> searchDOs(int branchID,String column, String value) throws SQLException {
        ObservableList<DataOperator> operators = FXCollections.observableArrayList();
        String searchQuery = "SELECT operator_id, name, branch_id, contact, address, email, salary FROM data_operator WHERE branch_id = ? AND " + column + " LIKE ?";
        
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

    public void removeDO(int operatorId) throws SQLException {
        String deleteQuery = "DELETE FROM data_operator WHERE operator_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, operatorId);
            preparedStatement.executeUpdate();
        }
    }
}
