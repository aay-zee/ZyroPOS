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
        String selectQuery="SELECT * FROM branch";
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
        String selectQuery="SELECT manager_id, name, branch_id, phone, address, email FROM branch_manager";
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
        String searchQuery = "SELECT * FROM branch WHERE " + column + " LIKE ?";
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
        String searchQuery = "SELECT manager_id, name, branch_id, phone, address, email FROM branch_manager WHERE " + column + " LIKE ?";
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
        String deleteQuery = "DELETE FROM branch WHERE branch_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, branchId);
            preparedStatement.executeUpdate();
        }
    }

    public void removeBranchManager(int managerId) throws SQLException {
        String deleteQuery = "DELETE FROM branch_manager WHERE manager_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, managerId);
            preparedStatement.executeUpdate();
        }
    }
}
