package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class LoginController implements Initializable {
    @FXML
    protected JFXButton btnBack;

    @FXML
    protected JFXButton btnSubmit;

    @FXML
    protected Label lblLogin;

    @FXML
    protected Label lblRole;

    @FXML
    protected Label lblSystemName;

    @FXML
    protected PasswordField pfPassword;

    @FXML
    protected TextField tfUsername;

    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    public LoginController() {
        System.out.println("LoginController");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnBack.setFocusTraversable(false);
        btnSubmit.setFocusTraversable(false);
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Back");
        alert.setHeaderText("You're About to go Back!");
        alert.setContentText("Are you sure you want to go Back?");
        //alert.setGraphic(new ImageView("D:\\JAVAFX\\logout\\src\\main\\resources\\org\\example\\logout\\svgviewer-png-output.png"));
        if(alert.showAndWait().get() == ButtonType.OK) {
            root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/GeneralLogin.fxml")));
            stage=(Stage)lblRole.getScene().getWindow();
            scene=new Scene(root);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

            //add css

            stage.sizeToScene();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    @FXML
    public abstract void submit(ActionEvent event) throws SQLException, IOException;
}
