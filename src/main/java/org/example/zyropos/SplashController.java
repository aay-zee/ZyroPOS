package org.example.zyropos;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.util.Duration;

public class SplashController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ProgressIndicator myProgress;
    @FXML
    private Label myLabel;
    @FXML
    private Label lblHeading;

    public SplashController() {
        System.out.println("Constructor Called");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        increaseProgress();
    }

    public void increaseProgress(){
        Thread thread=new Thread(()->{
            double progress=0.0;

            while(progress<1.0){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

                progress+=Math.random()*0.1;
                if(progress>1.0){
                    progress=1.0;
                }

                double finalProgress=progress;
                Platform.runLater(()->{
                    //progressIndicator.setProgress(finalProgress);
                    myLabel.setText((int) (finalProgress*100)+"%");

                    if(finalProgress*100==100){
//                        myProgress.setDisable(true);
                        myLabel.setText("Loading Done");
                        PauseTransition pause=new PauseTransition(Duration.seconds(0.5));
                        pause.setOnFinished(event -> {
                            try {
                                switchToGeneralLogin();
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        });
                        pause.play();
                    }
                });
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    public void switchToGeneralLogin() throws IOException {
        root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/GeneralLogin.fxml")));
        stage=(Stage)myProgress.getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();
    }
}