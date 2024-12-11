package org.example.zyropos;

import database.model.LoginModel;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class CashierLoginController extends LoginController{

    public CashierLoginController() {
        System.out.println("CashierLoginController");
    }

    @Override
    public void submit(ActionEvent event) throws SQLException {
        LoginModel loginModel = new LoginModel();

        System.out.println(tfUsername.getText());
        System.out.println(pfPassword.getText());

        if(loginModel.authenticateUser("Cashier",tfUsername.getText(),pfPassword.getText())){
            try {
                openDashboard("fxml/Cashier.fxml","Cashier");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
