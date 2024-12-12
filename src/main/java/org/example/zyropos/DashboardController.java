package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import database.model.DashboardModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class DashboardController {
    private DashboardModel dashboardModel;

    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    public DashboardController() {
        dashboardModel = new DashboardModel();
    }


    public void showAlert(String title, String header, String content){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public boolean showAlertConfirmation(String title, String header, String content){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        if(alert.showAndWait().get()==ButtonType.OK){
            return true;
        }
        else{
            return false;
        }
    }

    public void logout(JFXButton btnLogout) throws IOException {


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
