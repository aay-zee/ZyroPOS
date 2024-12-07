package database.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginModel extends BaseModel{

    public LoginModel() {
    }

    public void addNewUser(String role,String username, String password) throws SQLException {

        String insertQuery="INSERT INTO "+role+" (username, password) VALUES (?,?)";

        PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);

        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);

        int rowsInserted=preparedStatement.executeUpdate();

        if(rowsInserted>0){
            System.out.println(role+" added successfully");
        }

        preparedStatement.close();
    }
}
