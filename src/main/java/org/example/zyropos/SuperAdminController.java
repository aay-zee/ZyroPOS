package org.example.zyropos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import database.model.SuperAdminModel;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SuperAdminController extends DashboardController implements Initializable {
    private final SuperAdminModel saModel;
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

    //Add Branch Manager Pane
    @FXML
    private TextField tfMID;
    @FXML
    private TextField tfMName;
    @FXML
    private TextField tfBID;
    @FXML
    private TextField tfContact;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;

    //View Reports
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private BorderPane reportPane;
    @FXML
    private JFXComboBox<String> cbReportType;
    @FXML
    private JFXComboBox<String> cbRange;

    private String[] reportType={"Sales","Remaining Stock","Profit"};
    private String[] range={"Daily","Weekly","Monthly","Yearly"};




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddBranch.setFocusTraversable(false);
        btnLogout.setFocusTraversable(false);
        btnAddBM.setFocusTraversable(false);
        btnVR.setFocusTraversable(false);

        cbReportType.getItems().addAll(reportType);
        cbRange.getItems().addAll(range);
        lineChart=new LineChart<>(x,y);
    }

//    @FXML
//    private void initialize() throws SQLException {
//        btnAddBranch.setFocusTraversable(false);
//        btnLogout.setFocusTraversable(false);
//        btnAddBM.setFocusTraversable(false);
//        btnVR.setFocusTraversable(false);
//
//        XYChart.Series series=new XYChart.Series();
//        series.getData().add(new XYChart.Data("1",23));
//    }

    public SuperAdminController() {
        saModel=new SuperAdminModel();
    }

    public void handleLogout() throws IOException {
        logout(btnLogout);
    }

    public void addBranch() {
        ToggleGroup status=new ToggleGroup();
        rdABActive.setToggleGroup(status);
        rdABUnactive.setToggleGroup(status);
        bmPane.setVisible(false);
        reportPane.setVisible(false);
        branchPane.setVisible(true);
    }

    public void addBranchDB() throws SQLException {
        boolean status=true;
        if(rdABUnactive.isSelected()){
            status=false;
        }
        saModel.addNewBranchToDatabase(Integer.parseInt(tfABBranchID.getText()),tfABBranchName.getText(),tfABCity.getText(),tfABAddress.getText(),tfABContact.getText(),Integer.parseInt(tfABEmp.getText()),status);

        showAlert("Add Branch","Branch Addition Status","Branch Added Successfully");

        branchPane.getChildren().stream().filter(node -> node instanceof TextField || node instanceof JFXRadioButton).forEach(node -> {
            if(node instanceof TextField){
                ((TextField) node).setText("");
            }
            else{
                ((JFXRadioButton) node).setSelected(false);
            }
        });
    }

    public void addBM() {
        reportPane.setVisible(false);
        branchPane.setVisible(false);
        bmPane.setVisible(true);
    }

    public void addBManagerDB() throws SQLException {
        saModel.addNewBManagerToDatabase(Integer.parseInt(tfMID.getText()),tfMName.getText(),Integer.parseInt(tfBID.getText()),tfContact.getText(),tfAddress.getText(),tfEmail.getText(),tfUsername.getText(),tfPassword.getText());

        showAlert("Add Branch Manager","Branch Manager Addition Status","Branch Manager Added Successfully");

        bmPane.getChildren().stream().filter(node -> node instanceof TextField).forEach(node -> {
            ((TextField) node).setText("");
        });
    }

    public void viewReport(){
        branchPane.setVisible(false);
        bmPane.setVisible(false);
        reportPane.setVisible(true);
    }
}
