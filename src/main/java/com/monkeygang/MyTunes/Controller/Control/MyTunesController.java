package com.monkeygang.MyTunes.Controller.Control;

import com.monkeygang.MyTunes.Application.BuisnessLogic.AudioParser;
import com.monkeygang.MyTunes.Application.BuisnessLogic.PlayManager;
import com.monkeygang.MyTunes.Application.ControlObjects.*;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    boolean isPlayingPlaylist = false;

    LinkedList<Song> allSongList;
    LinkedList<Playlist> allPlaylistList;
    Image songNote;

    public MyTunesController() throws SQLException {
    }


    @FXML
    public void initialize() throws IOException, InvalidDataException, UnsupportedTagException, SQLException {


        allSongList = new LinkedList<>();
        allPlaylistList = new LinkedList<>();

        TableviewSongColTitle.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        TableviewSongColArtist.setCellValueFactory(
                new PropertyValueFactory<>("artist")
        );

        TableviewPlaylistColName.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        TableviewPlaylistColSongs.setCellValueFactory(
                new PropertyValueFactory<>("NumberOfSongs")
        );

        TableviewSongsOnPlaylistColTitle.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );

        TableviewSongsOnPlaylistColArtist.setCellValueFactory(
                new PropertyValueFactory<>("artist")
        );

        final File folder = new File("src/main/resources/Songs/");
        listFilesForFolder(folder);

        Playlist playlist0 = new Playlist(100, "Kevin music");

        allPlaylistList.add(playlist0);

        for (Song song : allSongList) {
            SongDao.addSong(song);
            //midlertidig linje
            playlist0.addSong(song);
        }

        allPlaylistList.add(playlist0);


        for (Playlist playlist : allPlaylistList) {
            PLaylistDao.addPlaylist(playlist0);
        }

        //midlertidige linjer
        //playlist0.addSong(allSongList.get(0));
        //playlist0.addSong(allSongList.get(1));
        //playlist0.addSong(allSongList.get(2));
        //playlist0.addSong(allSongList.get(3));

        //midlertidig linje
        TableviewPlaylists.getItems().add(playlist0);


        playbackSpeed.setItems(FXCollections.observableArrayList("0.25", "0.50", "0.75", "1.0", "1.25", "1.50", "1.75", "2.00"));
        playbackSpeed.getSelectionModel().select("1.0");


        updateTableviewSongs();


        updateCurrentlyPlayingLabel();

        songNote = new Image("https://pngimg.com/uploads/music_notes/music_notes_PNG93.png");
        AlbumImageView.setImage(songNote);


    }

    public void listFilesForFolder(final File folder) throws IOException, InvalidDataException, UnsupportedTagException {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);

            } else {
                Song song = AudioParser.parseMp3(fileEntry);

                if (song != null) {
                    allSongList.add(song);

                }


            }
        }
    }


    @FXML
    private TableView<Playlist> TableviewPlaylists;

    @FXML
    private TableColumn<Playlist, String> TableviewPlaylistColName;

    @FXML
    private TableColumn<Playlist, String> TableviewPlaylistColSongs;

    @FXML
    private TableView<Song> TableviewSongs;


    @FXML
    private TableColumn<Song, String> TableviewSongColTitle;

    @FXML
    private TableColumn<Song, String> TableviewSongColArtist;


    @FXML
    private TableView<Song> TableviewSongsOnPlaylists;

    @FXML
    private TableColumn<Song, String> TableviewSongsOnPlaylistColTitle;

    @FXML
    private TableColumn<Song, String> TableviewSongsOnPlaylistColArtist;


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
    public Label currentTimeInSong;

    @FXML
    public Label songTotalDuration;

    @FXML
    private Button deletePlaylistButton;

    @FXML
    private Button deleteSongButton;

    @FXML
    private Button editPlaylistButton;

    @FXML
    private Button editSongButton;


    @FXML
    public void updateTableviewSongs() {
        TableviewSongs.getItems().clear();

        for (Song song : SongDao.getAllSongs()) {
            TableviewSongs.getItems().add(song);

        }

        //TableviewSongs.getItems().addAll(SongDao.getAllSongs());
    }


    public void updateCurrentlyPlayingLabel() {

        currentlyPlayingLabel.setText(playManager.currentlyPlayingSong(currentSong));

    }


    public void updateTableviewSongsOnPlaylist(MouseEvent event) {
        TableviewSongsOnPlaylists.getItems().clear();


        TableviewSongsOnPlaylists.getItems().addAll(SongDao.getAllSongs());

    }


    @FXML
    private ImageView AlbumImageView;
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
    void DeletePlaylist(MouseEvent event) {

    }

    @FXML
    void addSongToPlaylist(MouseEvent event) {

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
    void playlistChosen(MouseEvent event) {


        TableviewSongsOnPlaylists.getItems().clear();

        for (Song song : TableviewPlaylists.getSelectionModel().getSelectedItem().getSongList()) {
            System.out.println(song);
            TableviewSongsOnPlaylists.getItems().add(song);

        }

    }


    @FXML
    void resumePlay() {

        if (currentSong != null) {

            playManager.playSong(currentSong);

            if (currentSong.getAlbumCover() != null && currentSong.getAlbumCover().getWidth() > 0) {

                System.out.println("changing album cover");
                AlbumImageView.setImage(currentSong.getAlbumCover());

            } else {
                AlbumImageView.setImage(songNote);
                System.out.println("Song dosen't have an album cover");
            }
        } else {
            System.out.println("no song chosen");
        }

    }


    @FXML
    void songDrag() {
        drag_Flag = true;
    }

    @FXML
    void songChosen(MouseEvent event) {

        currentSong = TableviewSongs.getSelectionModel().getSelectedItem();
        isPlayingPlaylist = false;

        if (doubleClickTester(event)) {
            resumePlay();
        }

        //resumePlay(/*event*/);
        //playManager.playSong(currentSong, true);


    }

    @FXML
    void songOnPlaylistChosen(MouseEvent event) {
        isPlayingPlaylist = true;

        currentSong = TableviewSongsOnPlaylists.getSelectionModel().getSelectedItem();

        if (doubleClickTester(event)) {
            resumePlay();
        }

    }

    @FXML
    void close(ActionEvent event) {
        System.exit(1);
    }


    public boolean doubleClickTester(MouseEvent event) {

        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && !drag_Flag) {
            long diff = 0;
            if (time1 == 0 || System.currentTimeMillis() - time1 > 1000)
                time1 = System.currentTimeMillis();
            else
                time2 = System.currentTimeMillis();
            if (time1 != 0 && time2 != 0) {
                diff = time2 - time1;
                time1 = 0;
                time2 = 0;
            }

            System.out.println("time between clicks in ms: " + diff);
            if (diff > 0 && diff <= 500) {
                isdblClicked = true;

            } else {
                isdblClicked = false;

            }

            System.out.println("is double Click = " + isdblClicked);
            return isdblClicked;


        } else {
            drag_Flag = false;
            return false;

        }

    }

    public void changePlayButtonIcon(PlayManager.playState state) {

        /*
        playSong() returnere en boolean der svarer til om den spiller en sang:
        true: spiller
        false: spiller ikke
        koden kan nok skrives bedre
         */


        if (state == PlayManager.playState.PLAYING) {
            playButton.setText("||");
        } else {
            playButton.setText(">");
        }

    }

    @FXML
    public void changeSpeed() {

        playManager.changePlaybackSpeed(playbackSpeed.getSelectionModel().getSelectedItem());
        System.out.println(playbackSpeed.getSelectionModel().getSelectedItem());


    }


    @FXML
    void newSong(MouseEvent event) throws InvalidDataException, UnsupportedTagException, IOException, SQLException {

        System.out.println("adding new song");
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Choose song to add");

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Sound Files", "*.mp3")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {


            Path copied = Paths.get("src/main/resources/Songs/" + selectedFile.getName());
            Path originalPath = selectedFile.toPath();
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);


            File copiedFile = new File(copied.toUri());

            Song newSong = AudioParser.parseMp3(copiedFile);
            SongDao.addSong(newSong);
            allSongList.add(newSong);
            updateTableviewSongs();
        }
    }


    @FXML
    void editSong(MouseEvent event) {

    }

    @FXML
    void deleteSong(MouseEvent event) throws SQLException {
        Song songToDelete;
        songToDelete = TableviewSongs.getSelectionModel().getSelectedItem();
        TableviewSongs.getItems().remove(songToDelete);
        SongDao.deleteSong(songToDelete);

        AlbumImageView.setImage(songNote);

        File fileToDelete = new File("src/main/resources/Songs/" + songToDelete.getTitle() + ".mp3");


        if (fileToDelete.delete()) {
            playManager.stopPlaying();
            songProgressSlider.setValue(0);
            songTotalDuration.setText("0");
            currentlyPlayingLabel.setText("");
        }


    }


    @FXML
    void newPlaylist(MouseEvent event) {

    }

    @FXML
    void editPlaylist(MouseEvent event) {

    }

    @FXML
    void deletePlaylist(MouseEvent event) {

    }

    @FXML
    void playlistSongDelete(MouseEvent event) {

    }

    @FXML
    void playlistSongDown(MouseEvent event) {

    }

    @FXML
    void playlistSongUp(MouseEvent event) {
        Song songToMove;
        Song songbuffer;
        int songToMoveIndexBuffer;

        songToMove = TableviewSongs.getSelectionModel().getSelectedItem();


        TableviewSongsOnPlaylists.getSelectionModel().getSelectedItem();

    }

    public void songMoveHandler(boolean down) {
        Song songToMove;
        Song songbuffer;
        int songToMoveIndexBuffer;

        songToMove = TableviewSongs.getSelectionModel().getSelectedItem();


        //TableviewSongsOnPlaylists.get

    }


    @FXML
    void resumePlay(MouseEvent event) {

        resumePlay();

    }

    @FXML
    void playBack(MouseEvent event) {
        songChangeHandler(false);


    }

    @FXML
    void skipPlay(MouseEvent event) {
        songChangeHandler(true);
    }

    public void songChangeHandler(boolean isSkip) {
        int index = 0;
        int size = 0;
        int nextSongIndex;
        TableView<Song> currentTable;

        if (isPlayingPlaylist) {
            currentTable = TableviewSongsOnPlaylists;
        } else {
            currentTable = TableviewSongs;
        }

        index = currentTable.getItems().indexOf(currentSong);
        size = currentTable.getItems().size();


        if (isSkip) {
            if (index + 1 == size) {
                nextSongIndex = 0;

            } else {
                nextSongIndex = index + 1;

            }
        } else {
            if (index == 0) {
                nextSongIndex = size - 1;

            } else {
                nextSongIndex = index - 1;

            }
        }

        currentSong = currentTable.getItems().get(nextSongIndex);
        currentTable.getSelectionModel().select(nextSongIndex);


        resumePlay();

        System.out.println("changed song");


    }


}

