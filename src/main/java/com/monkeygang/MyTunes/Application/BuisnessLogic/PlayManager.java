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




    public void playSong(Song song, boolean... doubleClickCheck){

        doubleClicked = doubleClickCheck.length != 0;

        if (isPlaying && previousSong == song && !doubleClicked){
            this.mp.pause();
            isPlaying = false;
            controller.changePlayButtonIcon(isPlaying);

        }else{

            if (this.mp != null){
                this.mp.stop();
            }


            previousSong = song;
            String filepath = "src/main/resources/Songs/" + song.getName() + ".mp3";

            File f = new File(filepath);
            Media m = new Media(f.toURI().toString());

            this.mp = new MediaPlayer(m);
            mp.play();
            controller.changePlayButtonIcon(isPlaying);
            isPlaying = true;
        }

    }

    public boolean getIsPlaying(){
        return isPlaying;
    }

    public Song getPreviousSong(){
        return previousSong;
    }


}