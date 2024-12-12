package database.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import org.example.zyropos.Branch;
import org.example.zyropos.BranchManager;
import org.example.zyropos.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperAdminModel extends BaseModel{


    public SuperAdminModel() {
    }


    //    public void testConnection() throws SQLException {
//        if(connection.isValid(5)){
//            System.out.println("Connection Established");
//        }
//        else{
//            System.out.println("Connection Not Established");
//        }
//    }

    private void showAlert(String title,String header,String content){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void addNewBranchToDatabase(String branchName,String city,String address,String phone,int empCount,boolean isActive) throws SQLException {

        String insertQuery="INSERT INTO Branch (name, city, address, phone, employees_count, is_active) VALUES(?,?,?,?,?,?)";

        PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);

        preparedStatement.setString(1,branchName);
        preparedStatement.setString(2,city);
        preparedStatement.setString(3,address);
        preparedStatement.setString(4,phone);
        preparedStatement.setInt(5,empCount);
        preparedStatement.setBoolean(6,isActive);

        int rowsInserted=preparedStatement.executeUpdate();

        if(rowsInserted>0){
            System.out.println("Branch added successfully");
        }

        preparedStatement.close();
    }

    public void addNewBManagerToDatabase(String managerName,int branchID,String contact,String address,String email,String userName,String password) throws SQLException {

//        try {
            String insertQuery = "INSERT INTO BranchManager (name,branchID,phone,address,email,username,password) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, managerName);
            preparedStatement.setInt(2, branchID);
            preparedStatement.setString(3, contact);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, userName);
            preparedStatement.setString(7, password);

            int rowsInserted = preparedStatement.executeUpdate();

            if(rowsInserted>0){
                System.out.println("Branch Manager added successfully");
            }

            preparedStatement.close();
//        }catch (SQLException e){
//            if(e.getSQLState().equals("23000") || e.getErrorCode()==1452){
//                showAlert("Exception","Addition Failed","Foreign Key Constraint Violated");
//            }
//            else {
//                e.printStackTrace();
//            }
//        }
    }

    public ObservableList<Branch> getAllBranches() throws SQLException {
        ObservableList<Branch> branches = FXCollections.observableArrayList();
        String selectQuery="SELECT * FROM Branch";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
        ResultSet resultSet=preparedStatement.executeQuery();

        while(resultSet.next()){
            branches.add(new Branch(
                    resultSet.getInt("branchID"),
                    resultSet.getString("name"),
                    resultSet.getString("city"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"),
                    resultSet.getInt("employees_count"),
                    resultSet.getBoolean("is_active")
            ));
        }

        for(Branch branch:branches){
            System.out.println(branch.getBranchID()+" "+branch.getBranchName()+" "+branch.getCity()+" "+branch.getAddress()+" "+branch.getPhone()+" "+branch.getEmpCount()+" "+branch.isActive());
        }

        resultSet.close();
        preparedStatement.close();
        return branches;
    }

    public ObservableList<BranchManager> getAllBranchManagers() throws SQLException {
        ObservableList<BranchManager> branches = FXCollections.observableArrayList();
        String selectQuery="SELECT manager_id,name,branchID,phone,address,email FROM BranchManager";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
        ResultSet resultSet=preparedStatement.executeQuery();

        while(resultSet.next()){
            branches.add(new BranchManager(
                    resultSet.getInt("manager_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("branchID"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    resultSet.getString("email")
            ));
        }

        resultSet.close();
        preparedStatement.close();
        return branches;
    }

    public ObservableList<Branch> searchBranches(String column, String value) throws SQLException {
        ObservableList<Branch> branches = FXCollections.observableArrayList();
        String searchQuery = "SELECT * FROM Branch WHERE " + column + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
        preparedStatement.setString(1, "%" + value + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
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
        resultSet.close();
        preparedStatement.close();
        return branches;
    }

    public ObservableList<BranchManager> searchManagers(String column, String value) throws SQLException {
        ObservableList<BranchManager> managers = FXCollections.observableArrayList();
        String searchQuery = "SELECT manager_id,name,branchID,phone,address,email FROM BranchManager WHERE " + column + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
        preparedStatement.setString(1, "%" + value + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            managers.add(new BranchManager(
                    resultSet.getInt("manager_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("branchID"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    resultSet.getString("email")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        return managers;
    }

    public void removeBranch(int branchId) throws SQLException {
        String deleteQuery = "DELETE FROM Branch WHERE branchID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, branchId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void removeBranchManager(int managerId) throws SQLException {
        String deleteQuery = "DELETE FROM BranchManager WHERE manager_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, managerId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
