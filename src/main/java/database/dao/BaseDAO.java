package database.dao;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {

    public BaseDAO(){
    }
    
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public int getBranchID(String role,String username) {
        String query = "SELECT branchID FROM "+role+" WHERE username=?";
        int branchID = 0;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    branchID = resultSet.getInt("branchID");
                } else {
                    System.out.println("No branch ID found for the given username.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branchID;
    }
}
