package database.dao;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {

    public BaseDAO() {
    }

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public int getBranchID(String role, String username) {
        String tableName = "branch_manager"; // Default
        if (role.equals("BranchManager"))
            tableName = "branch_manager";
        else if (role.equals("DataOperator"))
            tableName = "data_operator";
        else if (role.equals("Cashier"))
            tableName = "cashier";

        String query = "SELECT branch_id FROM " + tableName + " WHERE username=?";
        int branchID = 0;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    branchID = resultSet.getInt("branch_id");
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
