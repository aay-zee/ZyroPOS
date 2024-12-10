package database.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel extends BaseModel{

    public LoginModel() {
    }

    public boolean authenticateUser(String role,String username, String password) throws SQLException {

        String insertQuery="SELECT username,password FROM " + role + " WHERE username=? AND password=?";

        PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);

        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);

        ResultSet result=preparedStatement.executeQuery();

        if(result.next()){
            System.out.println("Authentication Successful");
            do{
                System.out.println("Username: "+result.getString(1));
                System.out.println("Password: "+result.getString(2));
            }while(result.next());
            preparedStatement.close();
            return true;
        }
        else{
            System.out.println("Authentication Failed");
        }

        preparedStatement.close();
        return false;
    }
}
