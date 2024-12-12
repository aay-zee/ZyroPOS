package database.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.zyropos.Branch;
import org.example.zyropos.Cashier;
import org.example.zyropos.DataOperator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminModel extends BaseModel{

    public AdminModel() {

    }

    public void addNewEmployeeToDatabase(String role,int eID,String eName,int bID,String contact,String address,String email,String salary,String username,String password) throws SQLException {
        String insertQuery="INSERT INTO "+role+" VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);

        preparedStatement.setInt(1,eID);
        preparedStatement.setString(2,eName);
        preparedStatement.setInt(3,bID);
        preparedStatement.setString(4,contact);
        preparedStatement.setString(5,address);
        preparedStatement.setString(6,email);
        preparedStatement.setString(7,salary);
        preparedStatement.setString(8,username);
        preparedStatement.setString(9,password);

        int rowsInserted=preparedStatement.executeUpdate();

        if(rowsInserted>0){
            System.out.println("Employee added to the database");
        }

        preparedStatement.close();
    }

    public ObservableList<DataOperator> getAllOperators(int branchID) throws SQLException {
        ObservableList<DataOperator> operators = FXCollections.observableArrayList();
        String selectQuery="SELECT employeeID,employeeName,branchID,contact,address,email,salary FROM DataOperator WHERE branchID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1,branchID);
        ResultSet resultSet=preparedStatement.executeQuery();

        while(resultSet.next()){
            operators.add(new DataOperator(
                    resultSet.getInt("employeeID"),
                    resultSet.getString("employeeName"),
                    resultSet.getInt("branchID"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("salary")
            ));
        }

        resultSet.close();
        preparedStatement.close();
        return operators;
    }

    public ObservableList<Cashier> getAllCashiers(int brancID) throws SQLException {
        ObservableList<Cashier> cashiers = FXCollections.observableArrayList();
        String selectQuery="SELECT employeeID,employeeName,branchID,contact,address,email,salary FROM Cashier WHERE branchID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1,brancID);
        ResultSet resultSet=preparedStatement.executeQuery();

        while(resultSet.next()){
            cashiers.add(new Cashier(
                    resultSet.getInt("employeeID"),
                    resultSet.getString("employeeName"),
                    resultSet.getInt("branchID"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("salary")
            ));
        }

        resultSet.close();
        preparedStatement.close();
        return cashiers;
    }

    public ObservableList<Cashier> searchCashiers(int branchID,String column, String value) throws SQLException {
        ObservableList<Cashier> cashiers = FXCollections.observableArrayList();
        String searchQuery = "SELECT employeeID,employeeName,branchID,contact,address,email,salary FROM Cashier WHERE branchID = ? AND " + column + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
        preparedStatement.setInt(1, branchID);
        preparedStatement.setString(2, "%" + value + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cashiers.add(new Cashier(
                    resultSet.getInt("employeeID"),
                    resultSet.getString("employeeName"),
                    resultSet.getInt("branchID"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("salary")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        return cashiers;
    }

    public void removeCashier(int cashierId) throws SQLException {
        String deleteQuery = "DELETE FROM Cashier WHERE employeeID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, cashierId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ObservableList<DataOperator> searchDOs(int branchID,String column, String value) throws SQLException {
        ObservableList<DataOperator> operators = FXCollections.observableArrayList();
        String searchQuery = "SELECT employeeID,employeeName,branchID,contact,address,email,salary FROM DataOperator WHERE branchID = ? AND " + column + " LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
        preparedStatement.setInt(1, branchID);
        preparedStatement.setString(2, "%" + value + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            operators.add(new DataOperator(
                    resultSet.getInt("employeeID"),
                    resultSet.getString("employeeName"),
                    resultSet.getInt("branchID"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("salary")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        return operators;
    }

    public void removeDO(int operatorId) throws SQLException {
        String deleteQuery = "DELETE FROM DataOperator WHERE employeeID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, operatorId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
