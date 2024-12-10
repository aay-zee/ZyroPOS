package database.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public ObservableList<DataOperator> getAllOperators() throws SQLException {
        ObservableList<DataOperator> operators = FXCollections.observableArrayList();
        String selectQuery="SELECT employeeID,employeeName,branchID,contact,address,email,salary FROM DataOperator";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
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

    public ObservableList<Cashier> getAllCashiers() throws SQLException {
        ObservableList<Cashier> cashiers = FXCollections.observableArrayList();
        String selectQuery="SELECT employeeID,employeeName,branchID,contact,address,email,salary FROM Cashier";
        PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
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
}
