package database.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.zyropos.CashierProduct;
import org.example.zyropos.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierModel extends BaseModel{

    public CashierModel() {
    }


    public ObservableList<CashierProduct> getAllProducts(int branchID) throws SQLException {
        ObservableList<CashierProduct> products = FXCollections.observableArrayList();
        String selectQuery="SELECT productID,productName,category,salePrice,unitPrice,cartonPrice,quantity FROM Product WHERE branchID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);

        preparedStatement.setInt(1,branchID);

        ResultSet resultSet=preparedStatement.executeQuery();

        while(resultSet.next()){
            products.add(new CashierProduct(
                    resultSet.getInt("productID"),
                    resultSet.getString("productName"),
                    resultSet.getString("category"),
                    resultSet.getDouble("salePrice"),
                    resultSet.getDouble("unitPrice"),
                    resultSet.getDouble("cartonPrice"),
                    resultSet.getInt("quantity")
            ));
        }

        resultSet.close();
        preparedStatement.close();
        return products;
    }

    public ObservableList<CashierProduct> searchProducts(int branchID, String column, String value) throws SQLException {
        ObservableList<CashierProduct> products = FXCollections.observableArrayList();
        String searchQuery = "SELECT productID,productName,category,salePrice,unitPrice,cartonPrice,quantity FROM Product WHERE branchID = ? AND " + column + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
        preparedStatement.setInt(1, branchID);
        preparedStatement.setString(2, "%" + value + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            products.add(new CashierProduct(
                    resultSet.getInt("productID"),
                    resultSet.getString("productName"),
                    resultSet.getString("category"),
                    resultSet.getDouble("salePrice"),
                    resultSet.getDouble("unitPrice"),
                    resultSet.getDouble("cartonPrice"),
                    resultSet.getInt("quantity")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        return products;
    }

    public boolean updateProductQuantity(int productId, int soldQuantity, int branchId) throws SQLException {

        String qtyCheck="SELECT quantity FROM Product WHERE productID = ? AND branchID = ?";
        PreparedStatement pStmnt=connection.prepareStatement(qtyCheck);
        pStmnt.setInt(1,productId);
        pStmnt.setInt(2,branchId);
        ResultSet resultSet=pStmnt.executeQuery();
        resultSet.next();
        int quantity=resultSet.getInt("quantity");
        resultSet.close();
        pStmnt.close();

        if(soldQuantity<=quantity) {
            // SQL to update remaining quantity
            String sql = "UPDATE Product SET quantity = quantity - ? WHERE productID = ? AND branchID = ?";
            // Execute update
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, soldQuantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, branchId);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            return true;
        }
        else{
            if(soldQuantity>quantity){
                System.out.println("Insufficient quantity");
            }
        }
        return false;
    }

    public void addSaleRecord(int productId, int quantity, double amount, int branchId) throws SQLException {
        // SQL to insert individual product sale
        String sql = "INSERT INTO SaleDetails (productID, quantity, amount, branchID) VALUES (?, ?, ?, ?)";
        // Execute insert
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, quantity);
        preparedStatement.setDouble(3, amount);
        preparedStatement.setInt(4, branchId);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void addSaleTransaction(double totalAmount, int branchId, String cashierUsername) throws SQLException {
        // SQL to insert complete sale transaction
        String sql = "INSERT INTO Sales (totalAmount, branchID, cashierUsername, saleDate) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        // Execute insert
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, totalAmount);
        preparedStatement.setInt(2, branchId);
        preparedStatement.setString(3, cashierUsername);

        preparedStatement.executeUpdate();
        preparedStatement.close();



    }


}
