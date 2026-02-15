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

        // Select password hash for verification
        String selectQuery="SELECT password FROM " + tableName + " WHERE username=? AND is_deleted=FALSE";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, username);

            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    String storedHash = result.getString("password");
                    if (utilities.PasswordUtil.checkPassword(password, storedHash)) {
                         System.out.println("Authentication Successful");
                         return true;
                    }
                }
                System.out.println("Authentication Failed");
            }
        }
        return false;
    }
}
