package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import database.model.DashboardModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import utilities.Values;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
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

    private DashboardModel dashboardModel;

    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    public LoginController() {
        System.out.println("LoginController");
        dashboardModel=new DashboardModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnBack.setFocusTraversable(false);
        btnSubmit.setFocusTraversable(false);
    }

    private void showAlert(String title, String header, String content){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void openDashboard(String fxmlFile,String controllerName) throws IOException {
        String username=tfUsername.getText();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource(fxmlFile));
        //root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
        root=fxmlLoader.load();

        if(controllerName.equals("DataOperator")){
            DataOperatorController dataOperatorController=fxmlLoader.getController();
            dataOperatorController.setUsername(username);
        }
        else if(controllerName.equals("Cashier")) {
            CashierController cashierController=fxmlLoader.getController();
            cashierController.setUsername(username);
        }
        else if(controllerName.equals("Admin")) {
            AdminController adminController=fxmlLoader.getController();
            adminController.setUsername(username);
        }

        stage=(Stage)tfUsername.getScene().getWindow();
        scene=new Scene(root);

        //Values.setPersonName(user);

        //add css
        //scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
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

    public void checkFirstTimeLogin(String role,String username) throws SQLException {
        try {
            if (dashboardModel.checkFirstTimeStatus(role,username)) {
                TextInputDialog passwordDialog = new TextInputDialog();
                passwordDialog.setTitle("Change Password");
                passwordDialog.setHeaderText("First Time Login Detected");
                passwordDialog.setContentText("Please enter your new password:");

                Optional<String> result = passwordDialog.showAndWait();
                if (result.isPresent()) {
                    String newPassword = result.get();
                    dashboardModel.updatePassword(role,username, newPassword);
                    dashboardModel.updateFirstTimeStatus(role,username);

                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Success");
                    success.setHeaderText("Password Updated");
                    success.setContentText("Your password has been successfully changed.");
                    success.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error", "Could not check first time login status.");
        }
    }
}
