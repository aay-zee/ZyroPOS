package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO extends BaseDAO {

    public LoginDAO() {
    }

    public boolean authenticateUser(String role,String username, String password) throws SQLException {

        String tableName = switch (role) {
            case "BranchManager" -> "branch_manager";
            case "Cashier" -> "cashier";
            case "DataOperator" -> "data_operator";
            case "SuperAdmin" -> "super_admin";
            default -> throw new SQLException("Invalid role: " + role);
        };

        String insertQuery="SELECT username,password FROM " + tableName + " WHERE username=? AND password=?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    System.out.println("Authentication Successful");
                    // Assuming you might want to fetch more user info here later
                    return true;
                } else {
                    System.out.println("Authentication Failed");
                }
            }
        }
        return false;
    }
}
