package com.monkeygang.MyTunes.Controller.Control;

import com.monkeygang.MyTunes.Application.BuisnessLogic.AudioParser;
import com.monkeygang.MyTunes.Application.BuisnessLogic.PlayManager;
import com.monkeygang.MyTunes.Application.ControlObjects.*;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;



public class MyTunesController {



    Song currentSong;
    SongDaoImpl SongDao = new SongDaoImpl();
    PlaylistDaoImpl PLaylistDao = new PlaylistDaoImpl();


    PlayManager playManager = new PlayManager(this);

    //til at checke double click

    boolean drag_Flag;
    long time1;
    long time2;

    boolean isdblClicked;

    LinkedList<Song> allSongList;
    LinkedList<Playlist> allPlaylistList;


    public MyTunesController() throws SQLException {
    }


    @FXML
    public void initialize() throws IOException, InvalidDataException, UnsupportedTagException, SQLException {




        allSongList = new LinkedList<>();
        allPlaylistList = new LinkedList<>();


        final File folder = new File("src/main/resources/Songs/");
        listFilesForFolder(folder);

        Playlist playlist0 = new Playlist(100, "Kevin music");

        allPlaylistList.add(playlist0);

        for (Song song : allSongList){
            SongDao.addSong(song);
            //midlertidig linje
            playlist0.addSong(song);
        }

        allPlaylistList.add(playlist0);


        for (Playlist playlist : allPlaylistList){
            PLaylistDao.addPlaylist(playlist0);
        }

        //midlertidige linjer
        //playlist0.addSong(allSongList.get(0));
        //playlist0.addSong(allSongList.get(1));
        //playlist0.addSong(allSongList.get(2));
        //playlist0.addSong(allSongList.get(3));

        //midlertidig linje
        listviewPlaylist.getItems().add(playlist0);



        playbackSpeed.setItems(FXCollections.observableArrayList("0.25", "0.50", "0.75" , "1.0", "1.25", "1.50", "1.75", "2.00"));
        playbackSpeed.getSelectionModel().select("1.0");

        updateListViewSongs();
        //updateListViewPlaylists();


    }

    public void listFilesForFolder(final File folder) throws IOException, InvalidDataException, UnsupportedTagException {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);

            } else {
                Song song = AudioParser.parseMp3(fileEntry);

                if (!(Objects.equals(song.getTitle(), "error"))){
                    allSongList.add(song);
                    //listviewSongs.getItems().add(song);
                }


            }
        }
    }

    @FXML
    public Slider songProgressSlider;


    @FXML
    public Slider songVolumeSlider;

    @FXML
    public ComboBox<String> playbackSpeed;

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
    private ListView<Playlist> listviewPlaylist;

    public void updateListViewPlaylists(){
        listviewPlaylist.getItems().clear();
        if (PLaylistDao.getAllPlaylists() != null){
        listviewPlaylist.getItems().addAll(PLaylistDao.getAllPlaylists());
        }

    }

    @FXML
    private ListView<Song> listviewSongs = new ListView<>();

    public void updateListViewSongs(){
        listviewSongs.getItems().clear();
        listviewSongs.getItems().addAll(SongDao.getAllSongs());

    }

    @FXML
    private ListView<Song> listviewSongsOnPlaylist;

    public void updateListViewSongsOnPlaylist(MouseEvent event){
        listviewSongsOnPlaylist.getItems().clear();



        //listviewSongsOnPlaylist.getItems().addAll(SongDao.getAllSongs());

    }

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
    void progressSliderOnMousePressed(MouseEvent event) {
        playManager.progressSliderAdjust();

    }


    @FXML
    void volumeSliderOnMousePressed(MouseEvent event) {

        playManager.volumeSliderAdjust();

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

        listviewSongsOnPlaylist.getItems().clear();

        for (Song song : listviewPlaylist.getSelectionModel().getSelectedItem().getSongList()){
            listviewSongsOnPlaylist.getItems().add(song);

        }

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

    /* muligvis deprecated
    @FXML
    void playlistValgt(MouseEvent event) {
        listviewSongsOnPlaylist.getItems().clear();

        for (Song song : listviewPlaylist.getSelectionModel().getSelectedItem().getSongList()){
            listviewSongsOnPlaylist.getItems().add(song);

        }


    }*/

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




        if (doubleClickTester(event)){
            playManager.playSong(currentSong,true);
        }




    }

    @FXML
    void songOnPlaylistChosen(MouseEvent event) {

        currentSong = listviewSongsOnPlaylist.getSelectionModel().getSelectedItem();




        if (doubleClickTester(event)){
            playManager.playSong(currentSong,true);
        }

    }

    @FXML
    void close(ActionEvent event) {
        System.exit(1);
    }







    public boolean doubleClickTester(MouseEvent event){

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

            System.out.println("time between clicks in ms: " + diff);
            if( diff>0 && diff <= 300)
            {
                isdblClicked=true;

            }
            else
            {
                isdblClicked=false;

            }

            System.out.println("is double Click = "+isdblClicked);
            return isdblClicked;




        }else{
            drag_Flag = false;
            return false;

        }

    }

    public void changePlayButtonIcon(PlayManager.playState state){

        /*
        playSong() returnere en boolean der svarer til om den spiller en sang:
        true: spiller
        false: spiller ikke
        koden kan nok skrives bedre
         */



        if (state == PlayManager.playState.PLAYING){
            playButton.setText("||");
        }else{
            playButton.setText(">");
        }

    }

    @FXML
    public void changeSpeed(){

        playManager.changePlaybackSpeed(playbackSpeed.getSelectionModel().getSelectedItem());
        System.out.println(playbackSpeed.getSelectionModel().getSelectedItem());


    }


}
