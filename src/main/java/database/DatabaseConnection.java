package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import utilities.Config;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static HikariDataSource dataSource;

    private DatabaseConnection() {
        HikariConfig config = new HikariConfig();
        // Construct JDBC URL from .env
        String jdbcUrl = "jdbc:mysql://" + Config.get("DB_HOST", "localhost") + ":" + 
                         Config.get("DB_PORT", "3306") + "/" + 
                         Config.get("DB_NAME", "zyropos");
        
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(Config.get("DB_USER", "root"));
        config.setPassword(Config.get("DB_PASSWORD", ""));
        
        // Pool settings
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000); // 30 mins
        
        dataSource = new HikariDataSource(config);
        System.out.println("Database Connection Pool Started.");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeConnection(){
        if(dataSource != null && !dataSource.isClosed()){
            dataSource.close();
            System.out.println("Database connection pool closed.");
        }
    }
}
