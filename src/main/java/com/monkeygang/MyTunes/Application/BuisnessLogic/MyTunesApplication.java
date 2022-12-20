package com.monkeygang.MyTunes.Application.BuisnessLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class MyTunesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        System.out.println(
                        "<-. (`-')             <-. (`-')_ <-.(`-')  (`-')  _                      (`-')  _ <-. (`-')_            \n" +
                        "   \\(OO )_      .->      \\( OO) ) __( OO)  ( OO).-/     .->       .->    (OO ).-/    \\( OO) )    .->    \n" +
                        ",--./  ,-.)(`-')----. ,--./ ,--/ '-'. ,--.(,------. ,--.'  ,-. ,---(`-') / ,---.  ,--./ ,--/  ,---(`-') \n" +
                        "|   `.'   |( OO).-.  '|   \\ |  | |  .'   / |  .---'(`-')'.'  /'  .-(OO ) | \\ /`.\\ |   \\ |  | '  .-(OO ) \n" +
                        "|  |'.'|  |( _) | |  ||  . '|  |)|      /)(|  '--. (OO \\    / |  | .-, \\ '-'|_.' ||  . '|  |)|  | .-, \\ \n" +
                        "|  |   |  | \\|  |)|  ||  |\\    | |  .   '  |  .--'  |  /   /) |  | '.(_/(|  .-.  ||  |\\    | |  | '.(_/ \n" +
                        "|  |   |  |  '  '-'  '|  | \\   | |  |\\   \\ |  `---. `-/   /`  |  '-'  |  |  | |  ||  | \\   | |  '-'  |  \n" +
                        "`--'   `--'   `-----' `--'  `--' `--' '--' `------'   `--'     `-----'   `--' `--'`--'  `--'  `-----'   "
                );


        FXMLLoader fxmlLoader = new FXMLLoader(MyTunesApplication.class.getResource("/MyTunes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("πψτμηες");
        stage.setScene(scene);
        stage.show();




    }

    public static void main(String[] args) {
        launch();
    }
}