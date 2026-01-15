package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO extends BaseDAO {
    public DashboardDAO() {
    }

    public boolean checkFirstTimeStatus(String role,String username) throws SQLException {
        String query = "SELECT firstTime FROM "+role+" WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                boolean c = rs.next();
                System.out.println(c);
                return c;
            }
        }
    }

    public void updatePassword(String role,String username, String newPassword) throws SQLException {
        String query = "UPDATE "+role+" SET password = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, newPassword);
            pst.setString(2, username);
            pst.executeUpdate();
        }
    }

    public void updateFirstTimeStatus(String role,String username) throws SQLException {
        String query = "UPDATE "+role+" SET firstTime = false WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, username);
            pst.executeUpdate();
        }
    }
}
