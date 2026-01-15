package org.example.zyropos;

import database.dao.LoginDAO;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class CashierLoginController extends LoginController{

    public CashierLoginController() {
        System.out.println("CashierLoginController");
    }

    @Override
    public void submit(ActionEvent event) throws SQLException {
        LoginDAO loginDAO = new LoginDAO();

        System.out.println(tfUsername.getText());
        System.out.println(pfPassword.getText());

        if(loginDAO.authenticateUser("Cashier",tfUsername.getText(),pfPassword.getText())){
            try {

                openDashboard("fxml/Cashier.fxml","Cashier");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
