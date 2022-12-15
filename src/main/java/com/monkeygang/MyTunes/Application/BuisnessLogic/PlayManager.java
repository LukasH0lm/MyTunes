package com.monkeygang.MyTunes.Application.BuisnessLogic;

import com.monkeygang.MyTunes.Application.ControlObjects.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class PlayManager {


    public enum playState {
        PLAYING, PAUSED, STOPPED
    }

    playState currentplayState = playState.STOPPED;

    public MediaPlayer mp;

    /*boolean isPlaying = false;
    private final boolean repeat = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
*/
    private Song previousSong;
    double previousVolumeValue = 1.0;

    boolean doubleClicked;


    public PlayManager(

    ) {


    }


    public void playSong(Song song, boolean... doubleClickCheck) {

        doubleClicked = doubleClickCheck.length != 0;

        System.out.println("Play manager input song: " + song);
        if (previousSong != song) {
            if (this.mp != null) {
                this.mp.stop();
                previousVolumeValue = mp.volumeProperty().getValue();

            }


            currentplayState = playState.STOPPED;
        }
        previousSong = song;


        if (currentplayState == playState.STOPPED) {
            String filepath = "src/main/resources/Songs/" + song.getTitle() + ".mp3";
            File f = new File(filepath);
            Media m = new Media(f.toURI().toString());
            this.mp = new MediaPlayer(m);
            mp.play();
            currentplayState = playState.PLAYING;

            mp.setVolume(previousVolumeValue);
        } else if (currentplayState == playState.PLAYING) {
            if (doubleClicked) {
                this.mp.stop();
                currentplayState = playState.STOPPED;
                playSong(song);
            }

            System.out.println("pausing");
            this.mp.pause();
            currentplayState = playState.PAUSED;

        } else if (currentplayState == playState.PAUSED) {
            this.mp.play();
            currentplayState = playState.PLAYING;

        }


    }

    public void stopPlaying() {
        if (this.mp != null) {
            this.mp.stop();
            currentplayState = playState.STOPPED;

            mp.setVolume(previousVolumeValue);
        }
    }




    public String currentlyPlayingSong(Song song) {

        if (this.mp != null) {
            return "Song currently playing: " + song.getTitle();
        }
        return "Nothing is playing";

    }

    public void volumeSliderAdjust(double volume) {
        if (this.mp != null) {
            mp.setVolume(volume);
        }

    }


    public playState getCurrentplayState() {
        return currentplayState;
    }

    public Song getPreviousSong() {
        return previousSong;
    }

    public void changePlaybackSpeed(String selectedItem) {
        if (this.mp != null) {
            this.mp.setRate(Double.parseDouble(selectedItem));
        }


    }

    public double getpreviousvolume() {
        return previousVolumeValue;
    }

}
