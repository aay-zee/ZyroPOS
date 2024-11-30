package org.example.zyropos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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


    public void changeToSuperAdmin(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Change Super Admin");
        alert.setHeaderText("You're About to Login as Super Admin!");
        alert.setContentText("Are you sure you want to proceed?");
        alert.setGraphic(new ImageView("D:\\JAVAFX\\logout\\src\\main\\resources\\org\\example\\logout\\svgviewer-png-output.png"));
        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Scene Changed to Super Admin");
            //New Scene Logic
            stage.close();
        }
    }

    public void changeToAdmin(ActionEvent event) {
        System.out.println("Changed to Admin");
    }

    public void changeToCashier(ActionEvent event) {
        System.out.println("Changed to Cashier");
    }

    public void changeToDataOperator(ActionEvent event) {
        System.out.println("Changed to Data Operator");
    }

}
