package com.monkeygang.MyTunes.Application.ControlObjects;

import javafx.scene.image.Image;

public class Song {

    int id;
    String title;
    String artist;
    String album;
    String genre;
    Image albumCover;

    public Song(int id, String title, String artist, String album, String genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;


    }

    public Song(int id, String title, String artist, String album, String genre,Image albumCover) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.albumCover = albumCover;



    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return
                title + " - " + artist;
    }



    public String getAlbum() {
        return album;
    }

    public Image getAlbumCover() {
        return albumCover;
    }



}
