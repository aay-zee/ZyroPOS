package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import utilities.Config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public static void main(String[] args) {
        createTables();
    }

    public static void createTables() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            // 1. Branch Table
            String createBranch = "CREATE TABLE IF NOT EXISTS branch (" +
                    "branch_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "city VARCHAR(100), " +
                    "address VARCHAR(255), " +
                    "phone VARCHAR(20), " +
                    "employees_count INT DEFAULT 0, " +
                    "is_active BOOLEAN DEFAULT TRUE" +
                    ")";
            statement.execute(createBranch);
            System.out.println("Checked/Created table: branch");

            // 2. BranchManager Table
            String createManager = "CREATE TABLE IF NOT EXISTS branch_manager (" +
                    "manager_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "branch_id INT, " +
                    "phone VARCHAR(20), " +
                    "address VARCHAR(255), " +
                    "email VARCHAR(100), " +
                    "username VARCHAR(50) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "FOREIGN KEY (branch_id) REFERENCES branch(branch_id) ON DELETE SET NULL" +
                    ")";
            statement.execute(createManager);
            System.out.println("Checked/Created table: branch_manager");

            // 3. Cashier Table
            String createCashier = "CREATE TABLE IF NOT EXISTS cashier (" +
                    "cashier_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "branch_id INT, " +
                    "contact VARCHAR(20), " +
                    "address VARCHAR(255), " +
                    "email VARCHAR(100), " +
                    "salary VARCHAR(50), " +
                    "username VARCHAR(50) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "FOREIGN KEY (branch_id) REFERENCES branch(branch_id) ON DELETE SET NULL" +
                    ")";
            statement.execute(createCashier);
            System.out.println("Checked/Created table: cashier");

            // 4. DataOperator Table
            String createDataOperator = "CREATE TABLE IF NOT EXISTS data_operator (" +
                    "operator_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "branch_id INT, " +
                    "contact VARCHAR(20), " +
                    "address VARCHAR(255), " +
                    "email VARCHAR(100), " +
                    "salary VARCHAR(50), " +
                    "username VARCHAR(50) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "FOREIGN KEY (branch_id) REFERENCES branch(branch_id) ON DELETE SET NULL" +
                    ")";
            statement.execute(createDataOperator);
            System.out.println("Checked/Created table: data_operator");

            // 5. Vendor Table
            String createVendor = "CREATE TABLE IF NOT EXISTS vendor (" +
                    "vendor_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "branch_id INT, " +
                    "contact_person VARCHAR(100), " +
                    "phone VARCHAR(20), " +
                    "email VARCHAR(100), " +
                    "company_name VARCHAR(100), " +
                    "registration_date VARCHAR(50), " +
                    "FOREIGN KEY (branch_id) REFERENCES branch(branch_id) ON DELETE CASCADE" +
                    ")";
            statement.execute(createVendor);
            System.out.println("Checked/Created table: vendor");

            // 6. Product Table
            String createProduct = "CREATE TABLE IF NOT EXISTS product (" +
                    "product_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "vendor_id INT, " +
                    "branch_id INT, " +
                    "category VARCHAR(50), " +
                    "original_price DOUBLE, " +
                    "sale_price DOUBLE, " +
                    "unit_price DOUBLE, " +
                    "carton_price DOUBLE, " +
                    "quantity INT DEFAULT 0, " +
                    "FOREIGN KEY (vendor_id) REFERENCES vendor(vendor_id) ON DELETE SET NULL, " +
                    "FOREIGN KEY (branch_id) REFERENCES branch(branch_id) ON DELETE CASCADE" +
                    ")";
            statement.execute(createProduct);
            System.out.println("Checked/Created table: product");

            // 7. Sales Table
            String createSales = "CREATE TABLE IF NOT EXISTS sales (" +
                    "sale_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "branch_id INT, " +
                    "cashier_username VARCHAR(50), " +
                    "total_amount DOUBLE, " +
                    "sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (branch_id) REFERENCES branch(branch_id)" +
                    ")";
            statement.execute(createSales);
            System.out.println("Checked/Created table: sales");

            // 8. SaleDetails Table
            String createSaleDetails = "CREATE TABLE IF NOT EXISTS sale_details (" +
                    "detail_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "sale_id INT, " +
                    "product_id INT, " +
                    "quantity INT, " +
                    "amount DOUBLE, " +
                    "FOREIGN KEY (sale_id) REFERENCES sales(sale_id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (product_id) REFERENCES product(product_id)" +
                    ")";
            statement.execute(createSaleDetails);
            System.out.println("Checked/Created table: sale_details");
            
            // 9. SuperAdmin Table
             String createSuperAdmin = "CREATE TABLE IF NOT EXISTS super_admin (" +
                    "admin_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE, " +
                    "password VARCHAR(255)" +
                    ")";
            statement.execute(createSuperAdmin);
            System.out.println("Checked/Created table: super_admin");
            
            // Seed SuperAdmin if empty
            seedSuperAdmin(connection);

            System.out.println("Database Tables Initialized Successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void seedSuperAdmin(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT count(*) FROM super_admin")) {
            if (rs.next() && rs.getInt(1) == 0) {
                 stmt.executeUpdate("INSERT INTO super_admin (username, password) VALUES ('admin', 'admin123')");
                 System.out.println("Seeded default super_admin (admin/admin123)");
            }
        }
    }
}
