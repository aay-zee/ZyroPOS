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
                    "is_active BOOLEAN DEFAULT TRUE, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "is_deleted BOOLEAN DEFAULT FALSE" +
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
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "is_deleted BOOLEAN DEFAULT FALSE, " +
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
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "is_deleted BOOLEAN DEFAULT FALSE, " +
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
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "is_deleted BOOLEAN DEFAULT FALSE, " +
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
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "is_deleted BOOLEAN DEFAULT FALSE, " +
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
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "is_deleted BOOLEAN DEFAULT FALSE, " +
                    "FOREIGN KEY (vendor_id) REFERENCES vendor(vendor_id) ON DELETE SET NULL, " +
                    "FOREIGN KEY (branch_id) REFERENCES branch(branch_id) ON DELETE CASCADE" +
                    ")";
            statement.execute(createProduct);
            System.out.println("Checked/Created table: product");

            // 7. Sales Table
            String createSales = "CREATE TABLE IF NOT EXISTS sales (" +
                    "sale_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "total_amount DOUBLE, " +
                    "branch_id INT, " +
                    "cashier_username VARCHAR(50), " +
                    "sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "is_deleted BOOLEAN DEFAULT FALSE, " +
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
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (sale_id) REFERENCES sales(sale_id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (product_id) REFERENCES product(product_id)" +
                    ")";
            statement.execute(createSaleDetails);
            System.out.println("Checked/Created table: sale_details");
            
            // 9. SuperAdmin Table
             String createSuperAdmin = "CREATE TABLE IF NOT EXISTS super_admin (" +
                    "admin_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "is_deleted BOOLEAN DEFAULT FALSE" +
                    ")";
            statement.execute(createSuperAdmin);
            System.out.println("Checked/Created table: super_admin");
            
            // Seed SuperAdmin if empty
            seedSuperAdmin(connection);
            
            // Seed Branch and Employees
            seedBranch(connection);
            seedBranchManager(connection);
            seedCashier(connection);
            seedDataOperator(connection);

            System.out.println("Database Tables Initialized Successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void seedSuperAdmin(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT count(*) FROM super_admin")) {
            if (rs.next() && rs.getInt(1) == 0) {
                 // Use BCrypt to hash the default password 'admin123'
                 String hashedPassword = utilities.PasswordUtil.hashPassword("admin123");
                 // Use Prepared Statement for safety although this is static data
                 try (var pstmt = conn.prepareStatement("INSERT INTO super_admin (username, password) VALUES (?, ?)")) {
                     pstmt.setString(1, "admin");
                     pstmt.setString(2, hashedPassword);
                     pstmt.executeUpdate();
                 }
                 System.out.println("Seeded default super_admin (admin/admin123)");
            }
        }
    }

    private static void seedBranch(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT count(*) FROM branch")) {
            if (rs.next() && rs.getInt(1) == 0) {
                try (var pstmt = conn.prepareStatement("INSERT INTO branch (name, city, address, phone, employees_count, is_active) VALUES (?, ?, ?, ?, ?, ?)")) {
                    pstmt.setString(1, "Main Branch");
                    pstmt.setString(2, "Metropolis");
                    pstmt.setString(3, "123 Main St");
                    pstmt.setString(4, "555-0100");
                    pstmt.setInt(5, 3); // Initial dummy manager, cashier, DO
                    pstmt.setBoolean(6, true);
                    pstmt.executeUpdate();
                }
                System.out.println("Seeded default branch (Main Branch)");
            }
        }
    }

    private static void seedBranchManager(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT count(*) FROM branch_manager")) {
            if (rs.next() && rs.getInt(1) == 0) {
                String hashedPassword = utilities.PasswordUtil.hashPassword("manager123");
                try (var pstmt = conn.prepareStatement("INSERT INTO branch_manager (name, branch_id, phone, address, email, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                    pstmt.setString(1, "Alice Manager");
                    pstmt.setInt(2, 1); // Assuming Main Branch has ID 1
                    pstmt.setString(3, "555-0101");
                    pstmt.setString(4, "123 Manager Ave");
                    pstmt.setString(5, "manager@zyropos.com");
                    pstmt.setString(6, "manager");
                    pstmt.setString(7, hashedPassword);
                    pstmt.executeUpdate();
                }
                System.out.println("Seeded default branch_manager (manager/manager123)");
            }
        }
    }

    private static void seedCashier(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT count(*) FROM cashier")) {
            if (rs.next() && rs.getInt(1) == 0) {
                String hashedPassword = utilities.PasswordUtil.hashPassword("cashier123");
                try (var pstmt = conn.prepareStatement("INSERT INTO cashier (name, branch_id, contact, address, email, salary, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                    pstmt.setString(1, "Bob Cashier");
                    pstmt.setInt(2, 1); // Main Branch
                    pstmt.setString(3, "555-0102");
                    pstmt.setString(4, "456 Cashier Ln");
                    pstmt.setString(5, "cashier@zyropos.com");
                    pstmt.setString(6, "30000");
                    pstmt.setString(7, "cashier");
                    pstmt.setString(8, hashedPassword);
                    pstmt.executeUpdate();
                }
                System.out.println("Seeded default cashier (cashier/cashier123)");
            }
        }
    }

    private static void seedDataOperator(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT count(*) FROM data_operator")) {
            if (rs.next() && rs.getInt(1) == 0) {
                String hashedPassword = utilities.PasswordUtil.hashPassword("operator123");
                try (var pstmt = conn.prepareStatement("INSERT INTO data_operator (name, branch_id, contact, address, email, salary, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                    pstmt.setString(1, "Charlie Operator");
                    pstmt.setInt(2, 1); // Main Branch
                    pstmt.setString(3, "555-0103");
                    pstmt.setString(4, "789 Operator Rd");
                    pstmt.setString(5, "operator@zyropos.com");
                    pstmt.setString(6, "35000");
                    pstmt.setString(7, "operator");
                    pstmt.setString(8, hashedPassword);
                    pstmt.executeUpdate();
                }
                System.out.println("Seeded default data_operator (operator/operator123)");
            }
        }
    }
}
