package org.example.zyropos;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label lblRole;

    @FXML
    private JFXPasswordField pfPassword;

    @FXML
    private JFXTextField tfUsername;


    public LoginController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
