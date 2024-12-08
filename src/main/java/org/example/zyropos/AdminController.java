package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController extends DashboardController implements Initializable {

    @FXML
    private Pane emPane;

    @FXML
    private BorderPane cpPane;

    @FXML
    private JFXButton btnAddEmp;

    @FXML
    private JFXButton btnVR;

    @FXML
    private JFXButton btnCP;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXComboBox<String> cmbCurrency;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private Pane innerPane1;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private HBox rootScene;

    @FXML
    private AnchorPane sideAnchorPane;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfBID;

    @FXML
    private TextField tfContact;

    @FXML
    private TextField tfEID;

    @FXML
    private TextField tfEName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfSalary;

    @FXML
    private TextField tfUsername;

    private String[] role={"Data Entry Operator","Cashier"};
    private String[] currency={"PKR","USD","GBP"};

    public AdminController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddEmp.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);
        btnCP.setFocusTraversable(false);
        btnVR.setFocusTraversable(false);

        cmbRole.getItems().addAll(role);
        cmbCurrency.getItems().addAll(currency);
    }

    @FXML
    void displayEM(ActionEvent event) {
        cpPane.setVisible(false);
        emPane.setVisible(true);
    }

    @FXML
    void displayVR(){

    }

    @FXML
    void displayCP(){
        emPane.setVisible(false);
        cpPane.setVisible(true);
    }

    @FXML
    void handleLogout() throws IOException {
        logout(btnLogout);
    }

    @FXML
    void submit(ActionEvent event) {

    }

}
