package database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.zyropos.CashierProduct;
import org.example.zyropos.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierDAO extends BaseDAO {

    public CashierDAO() {
    }

    public double getTodaySales(int branchId) throws SQLException {
        String sql = "SELECT SUM(total_amount) FROM sales WHERE branch_id = ? AND DATE(sale_date) = CURRENT_DATE";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    public int getTransactionCount(int branchId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM sales WHERE branch_id = ? AND DATE(sale_date) = CURRENT_DATE";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public ObservableList<Sale> getRecentTransactions(int branchId) throws SQLException {
        ObservableList<Sale> sales = FXCollections.observableArrayList();
        String sql = "SELECT sale_id, total_amount, branch_id, cashier_username, sale_date FROM sales WHERE branch_id = ? ORDER BY sale_date DESC LIMIT 10";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sales.add(new Sale(
                        rs.getInt("sale_id"),
                        rs.getDouble("total_amount"),
                        rs.getInt("branch_id"),
                        rs.getString("cashier_username"),
                        rs.getTimestamp("sale_date")
                    ));
                }
            }
        }
        return sales;
    }


    public ObservableList<CashierProduct> getAllProducts(int branchID) throws SQLException {
        ObservableList<CashierProduct> products = FXCollections.observableArrayList();
        String selectQuery="SELECT product_id, name, category, sale_price, unit_price, carton_price, quantity FROM product WHERE branch_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            
            preparedStatement.setInt(1, branchID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new CashierProduct(
                            resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getString("category"),
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

    public ObservableList<CashierProduct> searchProducts(int branchID, String column, String value) throws SQLException {
        ObservableList<CashierProduct> products = FXCollections.observableArrayList();
        String searchQuery = "SELECT product_id, name, category, sale_price, unit_price, carton_price, quantity FROM product WHERE branch_id = ? AND " + column + " LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            
            preparedStatement.setInt(1, branchID);
            preparedStatement.setString(2, "%" + value + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new CashierProduct(
                            resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getString("category"),
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

    public boolean updateProductQuantity(int productId, int soldQuantity, int branchId) throws SQLException {
        String qtyCheck="SELECT quantity FROM product WHERE product_id = ? AND branch_id = ?";
        
        try (Connection connection = getConnection()) {
            int quantity;
            try (PreparedStatement pStmnt = connection.prepareStatement(qtyCheck)) {
                pStmnt.setInt(1, productId);
                pStmnt.setInt(2, branchId);
                try (ResultSet resultSet = pStmnt.executeQuery()) {
                    if (resultSet.next()) {
                        quantity = resultSet.getInt("quantity");
                    } else {
                        // Product not found in this branch
                        return false; 
                    }
                }
            }

            if(soldQuantity <= quantity) {
                // SQL to update remaining quantity
                String sql = "UPDATE product SET quantity = quantity - ? WHERE product_id = ? AND branch_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, soldQuantity);
                    preparedStatement.setInt(2, productId);
                    preparedStatement.setInt(3, branchId);
                    preparedStatement.executeUpdate();
                    return true;
                }
            } else {
                if(soldQuantity > quantity){
                    System.out.println("Insufficient quantity");
                }
            }
        }
        return false;
    }

    public void addSaleLineItem(int saleId, int productId, int quantity, double amount, int branchId) throws SQLException {
        // SQL to insert individual product sale linking to the EXPLICIT sale_id
        String sql = "INSERT INTO sale_details (sale_id, product_id, quantity, amount) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, saleId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, amount);
            preparedStatement.executeUpdate();
        }
    }

    public int createSaleTransaction(double totalAmount, int branchId, String cashierUsername) throws SQLException {
        // SQL to insert complete sale transaction and RETURN the generated ID
        String sql = "INSERT INTO sales (total_amount, branch_id, cashier_username, sale_date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, totalAmount);
            preparedStatement.setInt(2, branchId);
            preparedStatement.setString(3, cashierUsername);
            
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating sale failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating sale failed, no ID obtained.");
                }
            }
        }
    }
}
