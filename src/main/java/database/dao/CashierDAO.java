package database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.zyropos.CashierProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierDAO extends BaseDAO {

    public CashierDAO() {
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

    public void addSaleRecord(int productId, int quantity, double amount, int branchId) throws SQLException {
        // SQL to insert individual product sale linking to the latest sale for this branch
        // Note: Relies on sequential processing; concurrency safe only if serialized per branch.
        String sql = "INSERT INTO sale_details (product_id, quantity, amount, sale_id) VALUES (?, ?, ?, (SELECT MAX(sale_id) FROM sales WHERE branch_id=?))";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setInt(4, branchId);
            preparedStatement.executeUpdate();
        }
    }

    public void addSaleTransaction(double totalAmount, int branchId, String cashierUsername) throws SQLException {
        // SQL to insert complete sale transaction
        String sql = "INSERT INTO sales (total_amount, branch_id, cashier_username, sale_date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, totalAmount);
            preparedStatement.setInt(2, branchId);
            preparedStatement.setString(3, cashierUsername);
            preparedStatement.executeUpdate();
        }
    }
}
