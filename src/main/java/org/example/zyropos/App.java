package org.example.zyropos;

import database.dao.BaseDAO;
import database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utilities.Values;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            //Connecting to Database
            try {
                DatabaseConnection dbConnection = DatabaseConnection.getInstance();
                Runtime.getRuntime().addShutdownHook(new Thread(dbConnection::closeConnection));
                
                // Database connection is initialized
                // Schema creation is now manual via DatabaseSetup.main() or external script
                
            } catch (Exception e) {
                System.err.println("WARNING: Database connection failed. Starting in OFFLINE mode for UI testing.");
                e.printStackTrace();
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Splash.fxml")));
            Scene scene = new Scene(root);
            
            stage.getIcons().add(new Image(Values.LOGO));
            stage.setTitle("ZyroPOS");
            stage.setScene(scene);
            stage.setWidth(1280);
            stage.setHeight(720);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void stop() throws Exception {

    }

    public static void main(String[] args) {
        launch();
    }
}