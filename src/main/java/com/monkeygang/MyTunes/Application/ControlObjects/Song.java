package com.monkeygang.MyTunes.Application.ControlObjects;

public class Song {

String filepath;
String genre;

    public Song(String filepath, String genre) {
        this.filepath = filepath;
        this.genre = genre;
    }


    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
