package org.example.zyropos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import utilities.Values;

import java.io.IOException;
import java.util.Objects;

public class GeneralLoginController {
    @FXML
    private Label lblRoleSelect;
    @FXML
    private Button btnSA;
    @FXML
    private Button btnAB;
    @FXML
    private Button btnC;
    @FXML
    private Button btnDO;
    @FXML
    private AnchorPane scenePane;

    private Parent root;
    private Stage stage;
    private Scene scene;

    private void showConfirmationDialog(String title, String header, String content,String fxmlFile) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        if(alert.showAndWait().get() == ButtonType.OK){
            root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            stage=(Stage)scenePane.getScene().getWindow();
            scene=new Scene(root);

            //add css
            //scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

            stage.sizeToScene();
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            //stage.close();
        }

    }


    public void changeToSuperAdmin(ActionEvent event) throws IOException {
        showConfirmationDialog("Change To Super Admin","You're about to Login as Super Admin!","Are you sure you want to proceed","fxml/SuperAdmin.fxml");
    }

    public void changeToAdmin(ActionEvent event) throws IOException {
        showConfirmationDialog("Change To Admin","You're about to Login as Admin!","Are you sure you want to proceed","fxml/Admin.fxml");
    }

    public void changeToCashier(ActionEvent event) throws IOException {
        showConfirmationDialog("Change To Cashier","You're about to Login as Cashier!","Are you sure you want to proceed","fxml/Cashier.fxml");
    }

    public void changeToDataOperator(ActionEvent event) throws IOException {
        showConfirmationDialog("Change To Data Entry Operator","You're about to Login as Data Entry Operator!","Are you sure you want to proceed","fxml/DataOperator.fxml");
    }

}
