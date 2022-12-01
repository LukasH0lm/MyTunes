package com.monkeygang.MyTunes.Application.BuisnessLogic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MyTunesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyTunesApplication.class.getResource("/MyTunes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("πψτμηες");
        stage.setScene(scene);
        stage.show();


        //UUHUHUHUHHUH Eksempel på afspilning af lyd xD
        File f = new File("src/main/resources/monke.mp3");
        Media m = new Media(f.toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();


    }

    public static void main(String[] args) {
        launch();
    }
}