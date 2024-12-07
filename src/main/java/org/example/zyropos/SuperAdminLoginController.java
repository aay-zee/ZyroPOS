package org.example.zyropos;


import database.model.LoginModel;
import javafx.event.ActionEvent;

import java.sql.SQLException;


public class SuperAdminLoginController extends LoginController {

    public SuperAdminLoginController() {
        System.out.println("SuperAdminLoginController");
    }

    @Override
    public void submit(ActionEvent event) throws SQLException {
        LoginModel loginModel = new LoginModel();

        loginModel.authenticateUser("SuperAdmin",tfUsername.getText(),pfPassword.getText());
    }
}
