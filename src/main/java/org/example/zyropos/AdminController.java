package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import database.model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import utilities.Values;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController extends DashboardController implements Initializable {

    private AdminModel adminModel;

    @FXML
    private Pane emPane;

    @FXML
    private BorderPane cpPane;

    @FXML
    private BorderPane reportPane;

    @FXML
    private Label lblPerson;

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
        adminModel=new AdminModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblPerson.setText("Welcome "+ Values.PERSON_NAME);
        lblPerson.setAlignment(Pos.CENTER);

        btnAddEmp.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);
        btnCP.setFocusTraversable(false);
        btnVR.setFocusTraversable(false);

        cmbRole.getItems().addAll(role);
        cmbCurrency.getItems().addAll(currency);
    }


    @FXML
    void displayEM(ActionEvent event) {
        lblPerson.setVisible(false);
        reportPane.setVisible(false);
        cpPane.setVisible(false);
        emPane.setVisible(true);
    }

    @FXML
    void displayVR(){
        lblPerson.setVisible(false);
        cpPane.setVisible(false);
        emPane.setVisible(false);
        reportPane.setVisible(true);
    }

    @FXML
    void displayCP(){
        lblPerson.setVisible(false);
        reportPane.setVisible(false);
        emPane.setVisible(false);
        cpPane.setVisible(true);
    }

    @FXML
    void handleLogout() throws IOException {
        logout(btnLogout);
    }

    @FXML
    void submit() throws SQLException {
        adminModel.addNewEmployeeToDatabase(cmbRole.getValue(),Integer.parseInt(tfEID.getText()),tfEName.getText(),Integer.parseInt(tfBID.getText()),tfContact.getText(),tfAddress.getText(),tfEmail.getText(),tfSalary.getText(),tfUsername.getText(),pfPassword.getText());
    }

}
