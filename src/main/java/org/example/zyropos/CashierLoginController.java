package org.example.zyropos;

import database.model.LoginModel;
import javafx.event.ActionEvent;

import java.sql.SQLException;

public class CashierLoginController extends LoginController{

    public CashierLoginController() {
        System.out.println("CashierLoginController");
    }

    @Override
    void submit(ActionEvent event) throws SQLException {
        LoginModel loginModel = new LoginModel();

        loginModel.addNewUser("Cashier",tfUsername.getText(),pfPassword.getText());
    }
}
