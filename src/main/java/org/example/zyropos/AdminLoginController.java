package org.example.zyropos;


import database.dao.LoginDAO;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;


public class AdminLoginController extends LoginController {

    public AdminLoginController() {
        System.out.println("AdminLoginController");
    }

    @Override
    public void submit(ActionEvent event) throws SQLException, IOException {
        LoginDAO loginDAO = new LoginDAO();

        if(loginDAO.authenticateUser("BranchManager",tfUsername.getText(),pfPassword.getText())){

            openDashboard("fxml/Admin.fxml","Admin");
        }
    }
}
