package database.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.zyropos.Branch;
import org.example.zyropos.Product;
import org.example.zyropos.Vendor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataOperatorModel extends BaseModel{


    public DataOperatorModel() {
    }



    public void addVendor(String name,int branchID,String cPerson,String phone,String email,String cName,String regDate) throws SQLException {

        String insertQuery="INSERT INTO Vendor (vendorName, branchID, contactPerson, phone, email, companyName, registrationDate) VALUES(?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);

        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,branchID);
        preparedStatement.setString(3,cPerson);
        preparedStatement.setString(4,phone);
        preparedStatement.setString(5,email);
        preparedStatement.setString(6,cName);
        preparedStatement.setString(7,regDate);

        int rowsInserted=preparedStatement.executeUpdate();

        if(rowsInserted>0){
            System.out.println("Vendor added successfully");
        }

        preparedStatement.close();
    }

    public ObservableList<Vendor> getAllVendors(int branchID) throws SQLException {
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();
        String selectQuery="SELECT vendorID,vendorName,contactPerson,phone,email,companyName,registrationDate FROM Vendor WHERE branchID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);

        preparedStatement.setInt(1,branchID);

        ResultSet resultSet=preparedStatement.executeQuery();

        while(resultSet.next()){
            vendors.add(new Vendor(
                    resultSet.getInt("vendorID"),
                    resultSet.getString("vendorName"),
                    resultSet.getString("contactPerson"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("companyName"),
                    resultSet.getString("registrationDate")
            ));
        }

        resultSet.close();
        preparedStatement.close();
        return vendors;
    }

    public ObservableList<String> getVendorIDs(int branchID) throws SQLException {
        ObservableList<String> vendorIDs = FXCollections.observableArrayList();
        String selectQuery="SELECT vendorID FROM Vendor WHERE branchID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);

        preparedStatement.setInt(1,branchID);

        ResultSet resultSet=preparedStatement.executeQuery();

        while(resultSet.next()){
            vendorIDs.add(String.valueOf(resultSet.getInt("vendorID")));
        }

        resultSet.close();
        preparedStatement.close();
        return vendorIDs;
    }

    public void addProduct(String name,int vendorID,String category,int branchID,Double origPrice,Double sPrice,Double uPrice,Double cPrice,int qty) throws SQLException {

        String insertQuery="INSERT INTO Product (productName, vendorID, category, branchID, originalPrice, salePrice, unitPrice,cartonPrice,quantity) VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);

        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,vendorID);
        preparedStatement.setString(3,category);
        preparedStatement.setInt(4,branchID);
        preparedStatement.setDouble(5,origPrice);
        preparedStatement.setDouble(6,sPrice);
        preparedStatement.setDouble(7,uPrice);
        preparedStatement.setDouble(8,cPrice);
        preparedStatement.setInt(9,qty);

        int rowsInserted=preparedStatement.executeUpdate();

        if(rowsInserted>0){
            System.out.println("Product added successfully");
        }

        preparedStatement.close();
    }

    public ObservableList<Product> getAllProducts(int branchID) throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String selectQuery="SELECT productID,productName,vendorID,category,originalPrice,salePrice,unitPrice,cartonPrice,quantity FROM Product WHERE branchID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);

        preparedStatement.setInt(1,branchID);

        ResultSet resultSet=preparedStatement.executeQuery();

        while(resultSet.next()){
            products.add(new Product(
                    resultSet.getInt("productID"),
                    resultSet.getString("productName"),
                    resultSet.getInt("vendorID"),
                    resultSet.getString("category"),
                    resultSet.getDouble("originalPrice"),
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

    public ObservableList<Vendor> searchVendors(int branchID, String column, String value) throws SQLException {
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();
        String searchQuery = "SELECT vendorID,vendorName,contactPerson,phone,email,companyName,registrationDate FROM Vendor WHERE branchID = ? AND " + column + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
        preparedStatement.setInt(1, branchID);
        preparedStatement.setString(2, "%" + value + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        // Add vendors to list similar to getAllVendors()
        while (resultSet.next()) {
            vendors.add(new Vendor(
                    resultSet.getInt("vendorID"),
                    resultSet.getString("vendorName"),
                    resultSet.getString("contactPerson"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("companyName"),
                    resultSet.getString("registrationDate")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        return vendors;
    }

    public void removeVendor(int vendorId) throws SQLException {
        String deleteQuery = "DELETE FROM Vendor WHERE vendorID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, vendorId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    public ObservableList<Product> searchProducts(int branchID, String column, String value) throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String searchQuery = "SELECT productID,productName,vendorID,category,originalPrice,salePrice,unitPrice,cartonPrice,quantity FROM Product WHERE branchID = ? AND " + column + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
        preparedStatement.setInt(1, branchID);
        preparedStatement.setString(2, "%" + value + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("productID"),
                    resultSet.getString("productName"),
                    resultSet.getInt("vendorID"),
                    resultSet.getString("category"),
                    resultSet.getDouble("originalPrice"),
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

    public void removeProduct(int productId) throws SQLException {
        String deleteQuery = "DELETE FROM Product WHERE productID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, productId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
