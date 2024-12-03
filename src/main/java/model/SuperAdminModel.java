package model;

import java.sql.Connection;
import java.sql.SQLException;

public class SuperAdminModel {
    Connection connect;

    {
        try {
            connect = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
