package com.monkeygang.MyTunes.Controller.Control;

import com.monkeygang.MyTunes.Application.BuisnessLogic.AudioParser;
import com.monkeygang.MyTunes.Application.BuisnessLogic.PlayManager;
import com.monkeygang.MyTunes.Application.ControlObjects.*;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;


public class MyTunesController {


    Song currentSong;
    SongDaoImpl SongDao = new SongDaoImpl();
    PlaylistDaoImpl PLaylistDao = new PlaylistDaoImpl();


    PlayManager playManager = new PlayManager();

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

        TableviewSongColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableviewSongColArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableviewPlaylistColName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableviewPlaylistColSongs.setCellValueFactory(new PropertyValueFactory<>("NumberOfSongs"));

        TableviewSongsOnPlaylistColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableviewSongsOnPlaylistColArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));

        /*final File folder = new File("src/main/resources/Songs/");
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
        midlertidige linjer
        playlist0.addSong(allSongList.get(0));
        playlist0.addSong(allSongList.get(1));
        playlist0.addSong(allSongList.get(2));
        playlist0.addSong(allSongList.get(3))
        midlertidig linje
        TableviewPlaylists.getItems().add(playlist0);*/


        playbackSpeed.setItems(FXCollections.observableArrayList("0.25", "0.50", "0.75", "1.0", "1.25", "1.50", "1.75", "2.00"));
        playbackSpeed.getSelectionModel().select("1.0");


        updateCurrentlyPlayingLabel();

        songNote = new Image("https://pngimg.com/uploads/music_notes/music_notes_PNG93.png");
        AlbumImageView.setImage(songNote);


        allPlaylistList = PLaylistDao.getAllPlaylists();

        System.out.println("Playlists in database: " + allPlaylistList);

        for (Playlist playlist : allPlaylistList) {
            LinkedList<Song> songsInThisPlaylist = PLaylistDao.getPlaylistSongs(playlist);
            for (Song song : songsInThisPlaylist) {
                playlist.addSong(song);
            }
        }

        TableviewPlaylists.getItems().addAll(allPlaylistList);

        updateUI();

        boolean updateSongsFromLocalStorage = false;

        if (updateSongsFromLocalStorage) {

            final File folder = new File("src/main/resources/Songs/");
            listFilesForFolder(folder);


            for (Song song : allSongList) {
                SongDao.addSong(song);
            }
        }
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

    public void updateUI() {

        Playlist selectedPlaylist = TableviewPlaylists.getSelectionModel().getSelectedItem();
        int selectedPlaylistIndex = TableviewPlaylists.getSelectionModel().getSelectedIndex();
        Song addedSong = TableviewSongs.getSelectionModel().getSelectedItem();

        updateTableviewPlaylist();
        updateTableviewSongsOnPlaylist();
        updateTableviewSongs();


        if (selectedPlaylist != null) {

            TableviewPlaylists.getSelectionModel().select(selectedPlaylistIndex);
            TableviewPlaylists.getSelectionModel().focus(selectedPlaylistIndex);

            for (Song song : selectedPlaylist.getSongList()) {
                TableviewSongsOnPlaylists.getItems().add(song);
            }

            if (addedSong != null) {
                TableviewSongsOnPlaylists.scrollTo(addedSong);
            }

        }
        TableviewSongsOnPlaylists.getItems().clear();


        TableviewPlaylists.getSelectionModel().select(selectedPlaylistIndex);
        TableviewPlaylists.getSelectionModel().focus(selectedPlaylistIndex);

        Playlist currentPlaylist = TableviewPlaylists.getSelectionModel().getSelectedItem();

        if (currentPlaylist != null) {

            TableviewSongsOnPlaylists.getItems().addAll(currentPlaylist.getSongList());
            TableviewSongsOnPlaylists.scrollTo(addedSong);
        }


    }

    @FXML
    private TableView<Playlist> TableviewPlaylists;

    @FXML
    private TableColumn<Playlist, String> TableviewPlaylistColName;

    @FXML
    private TableColumn<Playlist, String> TableviewPlaylistColSongs;


    @FXML
    public void updateTableviewPlaylist() {
        TableviewPlaylists.getItems().clear();
        for (Playlist playlist : PLaylistDao.getAllPlaylists()) {
            TableviewPlaylists.getItems().add(playlist);


        }


    }

    @FXML
    private TableView<Song> TableviewSongsOnPlaylists;

    @FXML
    private TableColumn<Song, String> TableviewSongsOnPlaylistColTitle;

    @FXML
    private TableColumn<Song, String> TableviewSongsOnPlaylistColArtist;


    public void updateTableviewSongsOnPlaylist() {
        TableviewSongsOnPlaylists.getItems().clear();


        Playlist currentPlaylist = TableviewPlaylists.getSelectionModel().getSelectedItem();

        if (currentPlaylist != null) {

            TableviewSongsOnPlaylists.getItems().addAll(currentPlaylist.getSongList());
        }
    }


    @FXML
    private TableView<Song> TableviewSongs;


    @FXML
    private TableColumn<Song, String> TableviewSongColTitle;

    @FXML
    private TableColumn<Song, String> TableviewSongColArtist;


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


    @FXML
    void DeletePlaylist(MouseEvent event) {

    }

    @FXML

    private Button addSongToaddSongToPlaylistButton;

    @FXML
    void addSongToPlaylist(MouseEvent event) throws SQLException {

        Playlist playlistToAddTo = TableviewPlaylists.getSelectionModel().getSelectedItem();
        int playlistToAddToIndex = TableviewPlaylists.getSelectionModel().getSelectedIndex();
        Song songToAdd = TableviewSongs.getSelectionModel().getSelectedItem();

        if (playlistToAddTo != null) {
            System.out.println("adding " + songToAdd + " to " + playlistToAddTo);
            playlistToAddTo.addSong(songToAdd);
            PLaylistDao.addSongToPlaylist(playlistToAddTo, songToAdd);
            updateUI();


        }


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

            ArrayList<String> minutesAndSeconds = null;

            playManager.playSong(currentSong);
            initializeProgressSlider();
            initializeVolumeSlider();
            currentTimeInSong();
            setPlaybackspeed();
            songVolumeSlider.setValue(playManager.getpreviousvolume());
            updateCurrentlyPlayingLabel();
            if (playManager.getCurrentplayState() == PlayManager.playState.STOPPED) {
                playbackSpeed.getSelectionModel().select("1.0");
                initializeProgressSlider();


            }


            changePlayButtonIcon(playManager.getCurrentplayState());


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
            if (time1 == 0 || System.currentTimeMillis() - time1 > 1000) time1 = System.currentTimeMillis();
            else time2 = System.currentTimeMillis();
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
    void newSong(MouseEvent event) throws InvalidDataException, UnsupportedTagException, IOException, SQLException {

        System.out.println("adding new song");
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Choose song to add");

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Sound Files", "*.mp3"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {


            Path copied = Paths.get("src/main/resources/Songs/" + selectedFile.getName());
            Path originalPath = selectedFile.toPath();
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);


            File copiedFile = new File(copied.toUri());

            Song newSong = AudioParser.parseMp3(copiedFile);
            SongDao.addSong(newSong);
            allSongList.add(newSong);
            updateUI();
        }
    }


    @FXML
    void editSong(MouseEvent event) {

    }

    @FXML
    void deleteSong(MouseEvent event) throws SQLException {
        Song songToDelete;
        songToDelete = TableviewSongs.getSelectionModel().getSelectedItem();

        AlbumImageView.setImage(songNote);

        File fileToDelete = new File("src/main/resources/Songs/" + songToDelete.getTitle() + ".mp3");


        if (fileToDelete.delete()) {
            playManager.stopPlaying();
            songProgressSlider.setValue(0);
            songTotalDuration.setText("0");
            currentlyPlayingLabel.setText("");

            TableviewSongs.getItems().remove(songToDelete);
            SongDao.deleteSong(songToDelete);
            for (Playlist playlist : PLaylistDao.getAllPlaylists()) {
                if (playlist.getSongList().contains(songToDelete)) {
                    PLaylistDao.deleteSongFromPlaylist(playlist, songToDelete);
                }
            }

            updateUI();


        }


    }


    @FXML
    void newPlaylist(MouseEvent event) throws InvalidDataException, UnsupportedTagException, IOException, SQLException {

        System.out.println("adding new playlist");
        Stage primaryStage = new Stage();


        Label label = new Label("Name of playlist");
        TextField tf = new TextField();
        Button btn = new Button("Add");


        HBox root = new HBox();
        root.setSpacing(20);
        root.getChildren().addAll(label, tf, btn);
        Scene scene = new Scene(root, 315, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add new playlist");
        primaryStage.show();

        btn.setOnAction(e ->
        {
            String userInput = tf.getText();


            //ID her er i princippet ligegyldigt, da vi sætter id i dao.
            //Vi burde at kunne slette id fra playlist, men vi venter til sidst.
            //Det id vi sætter ind her er useless, da den får et andet id i DB.

            Playlist newPlaylist = new Playlist(1, userInput);
            try {
                PLaylistDao.addPlaylist(newPlaylist);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            TableviewPlaylists.getItems().clear();
            TableviewPlaylists.getItems().addAll(allPlaylistList);

            updateUI();

            //Vi skal bruge database her i stedet,
            // men vi skal lige have det ordenligt op at køre.
            //allPlaylistList.add(newPlaylist);


        });
    }

    @FXML
    void editPlaylist(MouseEvent event) {

    }

    @FXML
    void deletePlaylist(MouseEvent event) throws SQLException {

        Playlist playlistToDelete;
        playlistToDelete = TableviewPlaylists.getSelectionModel().getSelectedItem();


        try {
            PLaylistDao.deletePlayList(playlistToDelete);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        updateUI();

        System.out.println(playlistToDelete + " has been deleted");

    }

    @FXML
    void playlistSongDelete(MouseEvent event) throws SQLException {

        Playlist playlistToDeleteFrom;
        Song songToDeleteFromPlaylist;
        songToDeleteFromPlaylist = TableviewSongsOnPlaylists.getSelectionModel().getSelectedItem();
        playlistToDeleteFrom = TableviewPlaylists.getSelectionModel().getSelectedItem();

        PLaylistDao.deleteSongFromPlaylist(playlistToDeleteFrom, songToDeleteFromPlaylist);

        updateUI();

        System.out.println("Song: " + songToDeleteFromPlaylist + " got deleted from playlist: " + playlistToDeleteFrom);

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

    @FXML
    public Slider songProgressSlider;


    @FXML
    public Slider songVolumeSlider;

    public void setVolume() {

        playManager.volumeSliderAdjust(songVolumeSlider.getValue());
        System.out.println(songVolumeSlider.getValue());


    }

    @FXML
    public ComboBox<String> playbackSpeed;

    public void setPlaybackspeed() {

        playManager.changePlaybackSpeed(playbackSpeed.getSelectionModel().getSelectedItem());
        System.out.println(playbackSpeed.getSelectionModel().getSelectedItem());


    }


    //ting der var i PlayManager som ikke skulle være der (dee var der fordi programmøren var inkompetent(programmøren var Sune))

    @FXML
    void progressSliderOnMousePressed(MouseEvent event) {
        progressSliderAdjust();

    }


    @FXML
    void volumeSliderOnMousePressed(MouseEvent event) {

        playManager.volumeSliderAdjust(songVolumeSlider.getValue());

    }

    public void currentTimeInSong() {


        if (playManager.mp != null) {

            playManager.mp.statusProperty().addListener((obsS, oldStatus, newStatus) -> {
                int songDuration = (int) playManager.mp.getTotalDuration().toSeconds();
                int[] split = splitTime(songDuration);
                songTotalDuration.setText(split[0] + "," + split[1]);
            });

            playManager.mp.currentTimeProperty().addListener((obsT, oldTime, newTime) -> {
                int currentTime = (int) playManager.mp.getCurrentTime().toSeconds();
                int[] split = splitTime(currentTime);
                currentTimeInSong.setText(split[0] + "," + split[1] + " /");
            });

        }

    }

    public static int[] splitTime(int seconds) {

        int minutes = seconds / 60;
        seconds = seconds - (minutes * 60);

        return new int[]{minutes, seconds};
    }


    public void initializeProgressSlider() {

        if (playManager.mp != null) {

            playManager.mp.statusProperty().addListener((obsS, oldStatus, newStatus) -> {
                songProgressSlider.setMax(playManager.mp.getTotalDuration().toSeconds());
                playManager.mp.currentTimeProperty().addListener((obsT, oldTime, newTime) -> songProgressSlider.setValue(newTime.toSeconds()));

            });
        }
    }

    public void progressSliderAdjust() {
        if (playManager.mp != null) {
            playManager.mp.seek(Duration.seconds(songProgressSlider.getValue()));
        }

    }

    public void initializeVolumeSlider() {

        if (playManager.mp != null) {

            playManager.mp.statusProperty().addListener((obsS, oldStatus, newStatus) -> {
                playManager.mp.volumeProperty().addListener((obsV, oldVol, newVol) -> songVolumeSlider.setValue((Double) newVol));

            });


        }
    }

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


}