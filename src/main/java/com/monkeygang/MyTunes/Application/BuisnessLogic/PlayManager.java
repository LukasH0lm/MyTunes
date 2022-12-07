package com.monkeygang.MyTunes.Application.BuisnessLogic;

import com.monkeygang.MyTunes.Application.ControlObjects.Song;
import com.monkeygang.MyTunes.Controller.Control.MyTunesController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class PlayManager {


    public enum playState {
            PLAYING,
            PAUSED,
            STOPPED
    }

    playState currentplayState = playState.STOPPED;

    MyTunesController controller;
    private MediaPlayer mp;

    boolean isPlaying = false;
    private final boolean repeat = false;
    private boolean atEndOfMedia = false;
    private Duration duration;

    private Song previousSong;
    double previousVolumeValue = 1.0;

    boolean doubleClicked;



    public PlayManager(MyTunesController controller){

        this.controller = controller;





    }





    public  void progressSlider() {
        controller.songProgressSlider.setMax(this.mp.getTotalDuration().toSeconds());
        this.mp.currentTimeProperty().addListener((obs, oldTime, newTime) ->
                controller.songProgressSlider.setValue(newTime.toSeconds()));
    }

    public void initializeProgressSlider() {

        if (this.mp != null) {

                this.mp.statusProperty().addListener((obs, oldStatus, newStatus) -> {
                        progressSlider();

                });
        }
    }

    public void progressSliderAdjust(){
        this.mp.seek(Duration.seconds(controller.songProgressSlider.getValue()));

    }

    public  void volumeSlider() {
        mp.volumeProperty().addListener((obs, oldVol, newVol) ->
                controller.songVolumeSlider.setValue((Double) newVol));



    }

    public void initializeVolumeSlider() {

        if (mp != null) {

            mp.statusProperty().addListener((obs, oldStatus, newStatus) -> {
                volumeSlider();

            });

        }
    }

    public void volumeSliderAdjust(){
        mp.setVolume(controller.songVolumeSlider.getValue());

    }



    public void playSong(Song song, boolean... doubleClickCheck){

        doubleClicked = doubleClickCheck.length != 0;

        System.out.println("Play manager input song: " + song);
        if (previousSong != song){
            if(this.mp != null){
                this.mp.stop();
                previousVolumeValue = mp.volumeProperty().getValue();

            }


            currentplayState = playState.STOPPED;
        }
        previousSong = song;


        if (currentplayState == playState.STOPPED){
            String filepath = "src/main/resources/Songs/" + song.getTitle() + ".mp3";
            File f = new File(filepath);
            Media m = new Media(f.toURI().toString());
            this.mp = new MediaPlayer(m);
            mp.play();
            volumeSlider();


            currentplayState = playState.PLAYING;
            controller.playbackSpeed.getSelectionModel().select("Normal");
            initializeProgressSlider();
            initializeVolumeSlider();
            controller.songVolumeSlider.setValue(previousVolumeValue);
            mp.setVolume(previousVolumeValue);
        }

        else if (currentplayState == playState.PLAYING){
            if (doubleClicked){
                this.mp.stop();
                currentplayState = playState.STOPPED;
                playSong(song);
            }

            System.out.println("pausing");
            this.mp.pause();
            currentplayState = playState.PAUSED;

        }

        else if (currentplayState == playState.PAUSED){
            this.mp.play();
            currentplayState = playState.PLAYING;

        }

        controller.changePlayButtonIcon(currentplayState);




    }

    public playState getCurrentplayState(){
        return currentplayState;
    }

    public Song getPreviousSong(){
        return previousSong;
    }

    public void changePlaybackSpeed(String selectedItem) {
        if (this.mp != null) {
            if (selectedItem == "normal") {
                this.mp.setRate(1.0);
            } else {
                this.mp.setRate(Double.parseDouble(selectedItem));
            }
        }

    }

}
