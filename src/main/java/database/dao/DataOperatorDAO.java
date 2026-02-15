package database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.zyropos.Product;
import org.example.zyropos.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataOperatorDAO extends BaseDAO {

    public DataOperatorDAO() {
    }

    public int getTotalVendors(int branchId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vendor WHERE branch_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getTotalProducts(int branchId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product WHERE branch_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getLowStockCount(int branchId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product WHERE branch_id = ? AND quantity < 10";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public ObservableList<Product> getLowStockItems(int branchId) throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product WHERE branch_id = ? AND quantity < 10 ORDER BY quantity ASC LIMIT 10";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getInt("vendor_id"),
                            rs.getString("category"),
                            rs.getDouble("original_price"),
                            rs.getDouble("sale_price"),
                            rs.getDouble("unit_price"),
                            rs.getDouble("carton_price"),
                            rs.getInt("quantity")
                    ));
                }
            }
        }
        return products;
    }

    public void addVendor(String name,int branchID,String cPerson,String phone,String email,String cName,String regDate) throws SQLException {

        String insertQuery="INSERT INTO vendor (name, branch_id, contact_person, phone, email, company_name, registration_date) VALUES(?,?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, branchID);
            preparedStatement.setString(3, cPerson);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, cName);
            preparedStatement.setString(7, regDate);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Vendor added successfully");
            }
        }
    }

    public ObservableList<Vendor> getAllVendors(int branchID) throws SQLException {
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();
        String selectQuery="SELECT vendor_id, name, contact_person, phone, email, company_name, registration_date FROM vendor WHERE branch_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            
            preparedStatement.setInt(1, branchID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vendors.add(new Vendor(
                            resultSet.getInt("vendor_id"),
                            resultSet.getString("name"),
                            resultSet.getString("contact_person"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("company_name"),
                            resultSet.getString("registration_date")
                    ));
                }
            }
        }
        return vendors;
    }

    public ObservableList<String> getVendorIDs(int branchID) throws SQLException {
        ObservableList<String> vendorIDs = FXCollections.observableArrayList();
        String selectQuery="SELECT vendor_id FROM vendor WHERE branch_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            
            preparedStatement.setInt(1, branchID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vendorIDs.add(String.valueOf(resultSet.getInt("vendor_id")));
                }
            }
        }
        return vendorIDs;
    }

    public void addProduct(String name,int vendorID,String category,int branchID,Double origPrice,Double sPrice,Double uPrice,Double cPrice,int qty) throws SQLException {

        String insertQuery="INSERT INTO product (name, vendor_id, category, branch_id, original_price, sale_price, unit_price, carton_price, quantity) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, vendorID);
            preparedStatement.setString(3, category);
            preparedStatement.setInt(4, branchID);
            preparedStatement.setDouble(5, origPrice);
            preparedStatement.setDouble(6, sPrice);
            preparedStatement.setDouble(7, uPrice);
            preparedStatement.setDouble(8, cPrice);
            preparedStatement.setInt(9, qty);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Product added successfully");
            }
        }
    }

    public ObservableList<Product> getAllProducts(int branchID) throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String selectQuery="SELECT product_id, name, vendor_id, category, original_price, sale_price, unit_price, carton_price, quantity FROM product WHERE branch_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            
            preparedStatement.setInt(1, branchID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new Product(
                            resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("vendor_id"),
                            resultSet.getString("category"),
                            resultSet.getDouble("original_price"),
                            resultSet.getDouble("sale_price"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getDouble("carton_price"),
                            resultSet.getInt("quantity")
                    ));
                }
            }
        }
        return products;
    }

    public ObservableList<Vendor> searchVendors(int branchID, String column, String value) throws SQLException {
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();
        String searchQuery = "SELECT vendor_id, name, contact_person, phone, email, company_name, registration_date FROM vendor WHERE branch_id = ? AND " + column + " LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            
            preparedStatement.setInt(1, branchID);
            preparedStatement.setString(2, "%" + value + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vendors.add(new Vendor(
                            resultSet.getInt("vendor_id"),
                            resultSet.getString("name"),
                            resultSet.getString("contact_person"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("company_name"),
                            resultSet.getString("registration_date")
                    ));
                }
            }
        }
        return vendors;
    }

    public void removeVendor(int vendorId) throws SQLException {
        String deleteQuery = "DELETE FROM vendor WHERE vendor_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, vendorId);
            preparedStatement.executeUpdate();
        }
    }


    public ObservableList<Product> searchProducts(int branchID, String column, String value) throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String searchQuery = "SELECT product_id, name, vendor_id, category, original_price, sale_price, unit_price, carton_price, quantity FROM product WHERE branch_id = ? AND " + column + " LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            
            preparedStatement.setInt(1, branchID);
            preparedStatement.setString(2, "%" + value + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new Product(
                            resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("vendor_id"),
                            resultSet.getString("category"),
                            resultSet.getDouble("original_price"),
                            resultSet.getDouble("sale_price"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getDouble("carton_price"),
                            resultSet.getInt("quantity")
                    ));
                }
            }
        }
        return products;
    }

    public void removeProduct(int productId) throws SQLException {
        String deleteQuery = "DELETE FROM product WHERE product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        }
    }
}
