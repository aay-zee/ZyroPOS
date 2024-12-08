package database.model;

import java.sql.PreparedStatement;
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
}
