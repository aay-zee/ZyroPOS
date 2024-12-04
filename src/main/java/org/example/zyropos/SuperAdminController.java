package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SuperAdminModel;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SuperAdminController {
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnAddBranch;
    @FXML
    private JFXButton btnAddBM;
    @FXML
    private JFXButton btnVR;
    @FXML
    private Pane bmPane;

    //Add Branch Pane
    @FXML
    private Pane branchPane;
    @FXML
    private TextField tfABBranchID;
    @FXML
    private TextField tfABBranchName;
    @FXML
    private TextField tfABCity;
    @FXML
    private TextField tfABAddress;
    @FXML
    private TextField tfABContact;
    @FXML
    private TextField tfABEmp;
    @FXML
    private JFXRadioButton rdABActive;
    @FXML
    private JFXRadioButton rdABUnactive;



    private Parent root;
    private Stage stage;
    private Scene scene;


    @FXML
    private void initialize() throws SQLException {
        btnAddBranch.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);
        btnAddBM.setFocusTraversable(false);
        btnVR.setFocusTraversable(false);
        SuperAdminModel.testConnection();
    }


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

    public void addBranch(ActionEvent event) throws IOException {
        ToggleGroup status=new ToggleGroup();
        rdABActive.setToggleGroup(status);
        rdABUnactive.setToggleGroup(status);
        bmPane.setVisible(false);
        branchPane.setVisible(true);
    }

    public void addBranchToDatabase() throws SQLException {
        boolean status=true;
        if(rdABUnactive.isSelected()){
            status=false;
        }
        SuperAdminModel.addNewBranchToDatabase(Integer.parseInt(tfABBranchID.getText()),tfABBranchName.getText(),tfABCity.getText(),tfABAddress.getText(),tfABContact.getText(),Integer.parseInt(tfABEmp.getText()),status);

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Branch");
        alert.setHeaderText("Branch Addition Status");
        alert.setContentText("Branch Added Successfully");
        alert.showAndWait();

        branchPane.getChildren().stream().filter(node -> node instanceof TextField || node instanceof JFXRadioButton).forEach(node -> {
            if(node instanceof TextField){
                ((TextField) node).setText("");
            }
            else{
                ((JFXRadioButton) node).setSelected(false);
            }
        });
    }

    public void addBM(ActionEvent event) throws IOException {
        branchPane.setVisible(false);
        bmPane.setVisible(true);
    }
}
