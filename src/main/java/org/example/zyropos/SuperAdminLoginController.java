package org.example.zyropos;

import database.dao.LoginDAO;
import javafx.event.ActionEvent;
import utilities.Values;

import java.io.IOException;
import java.sql.SQLException;

public class SuperAdminLoginController extends LoginController {

    public SuperAdminLoginController() {
        System.out.println("SuperAdminLoginController");
    }

    @Override
    public void submit(ActionEvent event) throws SQLException, IOException {
        LoginDAO loginDAO = new LoginDAO();
        System.out.println(tfUsername.getText());
        System.out.println(pfPassword.getText());

        if (loginDAO.authenticateUser("SuperAdmin", tfUsername.getText(), pfPassword.getText())) {
            Values.PERSON_NAME = tfUsername.getText();
            openDashboard("fxml/SuperAdmin.fxml", "SuperAdmin");
        }
    }
}
