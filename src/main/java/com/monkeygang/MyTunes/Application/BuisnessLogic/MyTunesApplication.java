package com.monkeygang.MyTunes.Application.BuisnessLogic;
import com.monkeygang.MyTunes.Application.ControlObjects.SongDaoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class MyTunesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyTunesApplication.class.getResource("/MyTunes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("πψτμηες");
        stage.setScene(scene);
        stage.show();


        //UUHUHUHUHHUH Eksempel på afspilning af lyd xD
        //jeg burde bare have blevet i min gruppe :[
        //File f = new File("src/main/resources/monke.mp3");
        //Media m = new Media(f.toURI().toString());
        //MediaPlayer mp = new MediaPlayer(m);
        //mp.play();

        new SongDaoImpl();


    }

    public static void main(String[] args) {
        launch();
    }
}