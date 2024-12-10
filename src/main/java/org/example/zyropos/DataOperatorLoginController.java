package org.example.zyropos;

import database.model.LoginModel;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class DataOperatorLoginController extends LoginController{

    public DataOperatorLoginController() {
        System.out.println("DataOperatorLoginController");
    }

//    @Override
//     submit(ActionEvent event) throws SQLException {
//        LoginModel loginModel = new LoginModel();
//
//        loginModel.addNewUser("DataOperator",tfUsername.getText(),pfPassword.getText());
//    }

    @Override
    public void submit(ActionEvent event) throws SQLException, IOException {
        LoginModel loginModel = new LoginModel();
        System.out.println(tfUsername.getText());
        System.out.println(pfPassword.getText());

        if(loginModel.authenticateUser("DataOperator",tfUsername.getText(),pfPassword.getText())){
            openDashboard("fxml/DataOperator.fxml");
        }
    }
}
