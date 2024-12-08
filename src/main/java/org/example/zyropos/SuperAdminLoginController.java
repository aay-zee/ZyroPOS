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

    public void superAdminScreen(String fxmlFile) throws IOException {
        root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
        stage=(Stage)tfUsername.getScene().getWindow();
        scene=new Scene(root);

        //add css
        //scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void submit(ActionEvent event) throws SQLException, IOException {
        LoginModel loginModel = new LoginModel();

        if(loginModel.authenticateUser("SuperAdmin",tfUsername.getText(),pfPassword.getText())){
            superAdminScreen("fxml/SuperAdmin.fxml");
        }
    }
}
