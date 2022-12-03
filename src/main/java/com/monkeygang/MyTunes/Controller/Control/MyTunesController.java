package com.monkeygang.MyTunes.Controller.Control;

import com.monkeygang.MyTunes.Application.BuisnessLogic.PlayManager;
import com.monkeygang.MyTunes.Application.ControlObjects.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;


public class MyTunesController {



    Song currentSong;
    PlayManager playManager = new PlayManager(this);

    //til at checke double click

    boolean drag_Flag;
    long time1;
    long time2;

    boolean isdblClicked;



    @FXML
    public void initialize() {
        Song careFree = new Song("Carefree", "Kevin Macleod", "carefree music");
        Song fluffingADuck = new Song("Fluffing a Duck", "Kevin Macleod", "Duck music");
        Song monkeysSpinningMonkeys = new Song("Monkeys Spinning Monkeys", "Kevin Macleod", "Monkey music");
        Song sneakySnitch = new Song("Sneaky Snitch","Kevin Macleod","Sneaky music");

        listviewSongs.getItems().add(careFree);
        listviewSongs.getItems().add(fluffingADuck);
        listviewSongs.getItems().add(monkeysSpinningMonkeys);
        listviewSongs.getItems().add(sneakySnitch);



    }

    @FXML
    private Button closeButton;

    @FXML
    private Label currentlyPlayingLabel;

    @FXML
    private Button deletePlaylistButton;

    @FXML
    private Button deleteSongButton;

    @FXML
    private Button editPlaylistButton;

    @FXML
    private Button editSongButton;

    @FXML
    private ListView<?> listviewPlaylist;

    @FXML
    private ListView<Song> listviewSongs = new ListView<>();



    @FXML
    private ListView<?> listviewSongsOnPlaylist;

    @FXML
    private Button newPlaylistButton;

    @FXML
    private Button newSongButton;

    @FXML
    private Button playBackButton;

    @FXML
    private Button playButton;

    @FXML
    private Label playlistLabel;

    @FXML
    private Button playlistSongDeleteButton;

    @FXML
    private Button playlistSongDownButton;

    @FXML
    private Button playlistSongUpButton;

    @FXML
    private Button skipPlayButton;

    @FXML
    private Label songLabel;

    @FXML
    private Label songOnPlaylistLabel;

    @FXML
    private ProgressBar songProgressBar;

    @FXML
    void DeletePlaylist(ActionEvent event) {

    }

    @FXML
    void addSongToPlaylist(ActionEvent event) {

    }


    @FXML
    void deleteSong(ActionEvent event) {

    }

    @FXML
    void editPlaylist(ActionEvent event) {

    }

    @FXML
    void editSong(ActionEvent event) {

    }

    @FXML
    void newPlaylist(ActionEvent event) {

    }

    @FXML
    void newSong(ActionEvent event) {

    }

    @FXML
    void playBack(ActionEvent event) {

    }

    @FXML
    void playlistChosen(MouseEvent event) {

    }

    @FXML
    void playlistSongDelete(ActionEvent event) {

    }

    @FXML
    void playlistSongDown(ActionEvent event) {

    }

    @FXML
    void playlistSongUp(ActionEvent event) {

    }

    @FXML
    void playlistValgt(MouseEvent event) {

    }

    @FXML
    void resumePlay(ActionEvent event) {


        playManager.playSong(currentSong);

    }

    @FXML
    void skipPlay(ActionEvent event) {

    }

    @FXML
    void songDrag(){
        drag_Flag = true;
    }
    @FXML
    void songChosen(MouseEvent event) {

        currentSong = listviewSongs.getSelectionModel().getSelectedItem();

        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && !drag_Flag) {
            long diff = 0;
            if(time1==0 || System.currentTimeMillis() - time1 > 1000)
                time1=System.currentTimeMillis();
            else
                time2=System.currentTimeMillis();
            if(time1!=0 && time2!=0){
                diff=time2-time1;
                time1 = 0;
                time2 = 0;
            }

            System.out.println(diff);
            if( diff>0 && diff <= 300)
            {
                isdblClicked=true;
                playManager.playSong(currentSong,true);
            }
            else
            {
                isdblClicked=false;
            }

            System.out.println("IsDblClicked()"+isdblClicked);




        }else{
            drag_Flag = false;

        }

    }

    @FXML
    void songOnPlaylistChosen(MouseEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
        System.exit(1);
    }

    public void changePlayButtonIcon(boolean state){

        /*
        playSong() returnere en boolean der svarer til om den spiller en sang:
        true: spiller
        false: spiller ikke
        koden kan nok skrives bedre
         */



        if (playManager.getIsPlaying()){
            playButton.setText("||");
        }else{
            playButton.setText(">KE");
        }

    }


}
