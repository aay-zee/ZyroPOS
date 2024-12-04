package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SuperAdminModel {

    public static void testConnection() throws SQLException {
        if(DatabaseConnector.getConnection().isValid(5)){
            System.out.println("Connection Established");
        }
        else{
            System.out.println("Connection Not Established");
        }
    }

    public static void addNewBranchToDatabase(int branchID,String branchName,String city,String address,String phone,int empCount,boolean isActive) throws SQLException {

        String insertQuery="INSERT INTO Branch VALUES(?,?,?,?,?,?,?)";

        Connection connection=DatabaseConnector.getConnection();

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
        connection.close();


    }
}
