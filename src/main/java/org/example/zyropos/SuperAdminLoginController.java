package org.example.zyropos;


import database.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class SuperAdminLoginController extends LoginController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    public SuperAdminLoginController() {
        System.out.println("SuperAdminLoginController");
    }


    @Override
    public void submit(ActionEvent event) throws SQLException, IOException {
        LoginModel loginModel = new LoginModel();
        System.out.println(tfUsername.getText());
        System.out.println(pfPassword.getText());

        if(loginModel.authenticateUser("SuperAdmin",tfUsername.getText(),pfPassword.getText())){
            openDashboard("fxml/SuperAdmin.fxml","SuperAdmin");
        }
    }
}
