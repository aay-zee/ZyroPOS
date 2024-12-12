package database.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardModel extends BaseModel {
    public DashboardModel() {
    }


    public boolean checkFirstTimeStatus(String role,String username) throws SQLException {
        String query = "SELECT firstTime FROM "+role+" WHERE username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, username);
        ResultSet rs = pst.executeQuery();
        boolean c=rs.next();
        System.out.println(c);
        return c;
    }

    public void updatePassword(String role,String username, String newPassword) throws SQLException {
        String query = "UPDATE "+role+" SET password = ? WHERE username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, newPassword);
        pst.setString(2, username);
        pst.executeUpdate();
    }

    public void updateFirstTimeStatus(String role,String username) throws SQLException {
        String query = "UPDATE "+role+" SET firstTime = false WHERE username = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, username);
        pst.executeUpdate();
    }
}
