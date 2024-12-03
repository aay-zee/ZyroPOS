package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "mysql://avnadmin:AVNS_MZKI03OsO5-UmIgx-Ez@mysql-zyropos-lhr-f8db.e.aivencloud.com:10838/defaultdb?ssl-mode=REQUIRED";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = "AVNS_MZKI03OsO5-UmIgx-Ez";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

