package com.monkeygang.MyTunes.Application.BuisnessLogic;

import com.monkeygang.MyTunes.Application.ControlObjects.Song;
import com.monkeygang.MyTunes.Controller.Control.MyTunesController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class PlayManager {

    MyTunesController controller;
    private MediaPlayer mp;

    boolean isPlaying = false;
    private final boolean repeat = false;
    private boolean atEndOfMedia = false;
    private Duration duration;

    private Song previousSong;

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




    public void playSong(Song song, boolean... doubleClickCheck){

        doubleClicked = doubleClickCheck.length != 0;

        System.out.println("Play manager input song: " + song);

        if (isPlaying && previousSong == song && !doubleClicked){
            this.mp.pause();
            isPlaying = false;

        }else {

            if (song != null) {


                if (this.mp != null) {
                    this.mp.stop();
                }


                previousSong = song;
                String filepath = "src/main/resources/Songs/" + song.getTitle() + ".mp3";

                File f = new File(filepath);
                Media m = new Media(f.toURI().toString());

                this.mp = new MediaPlayer(m);
                mp.play();

                initializeProgressSlider();

                isPlaying = true;


            }

        }

        controller.changePlayButtonIcon(isPlaying);
    }

    public boolean getIsPlaying(){
        return isPlaying;
    }

    public Song getPreviousSong(){
        return previousSong;
    }


}
