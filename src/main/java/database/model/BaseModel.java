package database.model;

import database.model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseModel {
    protected Connection connection;

    public BaseModel(){
        connection= DatabaseConnection.getInstance().getConnection();
    }

    public int getBranchID(String role,String username) {
        String query = "SELECT branchID FROM "+role+" WHERE username=?";
        int branchID = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                branchID = resultSet.getInt("branchID");
            } else {
                System.out.println("No branch ID found for the given username.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branchID;
    }
}
