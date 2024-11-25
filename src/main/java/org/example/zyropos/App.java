package org.example.zyropos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Splash.fxml")));
        Scene scene=new Scene(root);
        String css= Objects.requireNonNull(this.getClass().getResource("css/Splash.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(new Image("D:\\University (FAST)\\Semester 5\\Software Construction and Development\\ZyroPOS\\src\\main\\resources\\assets\\ZyroPOS1.png"));
        stage.setTitle("ZyroPOS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}