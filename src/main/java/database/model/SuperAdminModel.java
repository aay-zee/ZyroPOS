package database.model;

import javafx.scene.control.Alert;
import utilities.Values;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public void addNewBranchToDatabase(int branchID,String branchName,String city,String address,String phone,int empCount,boolean isActive) throws SQLException {

        String insertQuery="INSERT INTO Branch VALUES(?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);

        preparedStatement.setInt(1,branchID);
        preparedStatement.setString(2,branchName);
        preparedStatement.setString(3,city);
        preparedStatement.setString(4,address);
        preparedStatement.setString(5,phone);
        preparedStatement.setInt(6,empCount);
        preparedStatement.setBoolean(7,isActive);

        int rowsInserted=preparedStatement.executeUpdate();

        if(rowsInserted>0){
            System.out.println("Branch added successfully");
        }

        preparedStatement.close();
    }

    public void addNewBManagerToDatabase(int managerID,String managerName,int branchID,String contact,String address,String email,String userName,String password) throws SQLException {

//        try {
            String insertQuery = "INSERT INTO BranchManager VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, managerID);
            preparedStatement.setString(2, managerName);
            preparedStatement.setInt(3, branchID);
            preparedStatement.setString(4, contact);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, userName);
            preparedStatement.setString(8, password);

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
}
