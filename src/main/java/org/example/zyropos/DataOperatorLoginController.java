package org.example.zyropos;

import database.model.LoginModel;
import javafx.event.ActionEvent;

import java.sql.SQLException;

public class DataOperatorLoginController extends LoginController{

    public DataOperatorLoginController() {
        System.out.println("DataOperatorLoginController");
    }

    @Override
    void submit(ActionEvent event) throws SQLException {
        LoginModel loginModel = new LoginModel();

        loginModel.addNewUser("DataOperator",tfUsername.getText(),pfPassword.getText());
    }
}
