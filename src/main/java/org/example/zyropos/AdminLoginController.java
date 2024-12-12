package org.example.zyropos;


import database.model.LoginModel;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;


public class AdminLoginController extends LoginController {

    public AdminLoginController() {
        System.out.println("AdminLoginController");
    }

    @Override
    public void submit(ActionEvent event) throws SQLException, IOException {
        LoginModel loginModel = new LoginModel();

        if(loginModel.authenticateUser("BranchManager",tfUsername.getText(),pfPassword.getText())){
            checkFirstTimeLogin("BranchManager",tfUsername.getText());
            openDashboard("fxml/Admin.fxml","Admin");
        }
    }
}
