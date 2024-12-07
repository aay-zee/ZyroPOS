package org.example.zyropos;


import database.model.LoginModel;
import javafx.event.ActionEvent;

import java.sql.SQLException;


public class AdminLoginController extends LoginController {

    public AdminLoginController() {
        System.out.println("AdminLoginController");
    }

    @Override
    void submit(ActionEvent event) throws SQLException {
        LoginModel loginModel = new LoginModel();

        loginModel.addNewUser("Admin",tfUsername.getText(),pfPassword.getText());
    }
}
