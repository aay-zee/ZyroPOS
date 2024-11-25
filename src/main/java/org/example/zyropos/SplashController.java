package org.example.zyropos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {
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
                    }
                });
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
}