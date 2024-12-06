package database.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private final String HOST="mysql-zyropos-lhr-f8db.e.aivencloud.com";
    private final String PORT="10838";
    private final String DATABASE_NAME="zyropos";
    private final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME + "?sslmode=require";
    private final String USERNAME = "avnadmin";
    private final String PASSWORD = "AVNS_MZKI03OsO5-UmIgx-Ez";

    private DatabaseConnection() {
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connected to the online Database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Failed to Connect to the online Database.");
            System.exit(1);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        try {
            if(connection!=null && !connection.isClosed()){
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
