package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Objects;

public class SuperAdminController {
    @FXML
    private JFXButton btnLogout;

    private Parent root;
    private Stage stage;
    private Scene scene;



    public void logout(ActionEvent event) throws IOException {


        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're About to Logout!");
        alert.setContentText("Are you sure you want to Logout?");
        //alert.setGraphic(new ImageView("D:\\JAVAFX\\logout\\src\\main\\resources\\org\\example\\logout\\svgviewer-png-output.png"));
        if(alert.showAndWait().get() == ButtonType.OK) {
            root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/GeneralLogin.fxml")));
            stage=(Stage)btnLogout.getScene().getWindow();
            scene=new Scene(root);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

            //add css

            stage.sizeToScene();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }
}
