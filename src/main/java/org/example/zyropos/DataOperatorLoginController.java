package org.example.zyropos;

import database.dao.LoginDAO;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class DataOperatorLoginController extends LoginController{

    public DataOperatorLoginController() {
        System.out.println("DataOperatorLoginController");
    }

//    @Override
//     submit(ActionEvent event) throws SQLException {
//        LoginDAO loginDAO = new LoginDAO();
//
//        loginDAO.addNewUser("DataOperator",tfUsername.getText(),pfPassword.getText());
//    }

    @Override
    public void submit(ActionEvent event) throws SQLException, IOException {
        LoginDAO loginDAO = new LoginDAO();
        System.out.println(tfUsername.getText());
        System.out.println(pfPassword.getText());

        if(loginDAO.authenticateUser("DataOperator",tfUsername.getText(),pfPassword.getText())){

            openDashboard("fxml/DataOperator.fxml","DataOperator");
        }
    }
}
