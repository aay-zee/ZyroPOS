package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String HOST="mysql-zyropos-lhr-f8db.e.aivencloud.com";
    private static final String PORT="10838";
    private static final String DATABASE_NAME="zyropos";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME + "?sslmode=require";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = "AVNS_MZKI03OsO5-UmIgx-Ez";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

