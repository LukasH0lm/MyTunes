package com.monkeygang.MyTunes.Application.ControlObjects;

public class Song {

    String name;
    String author;
    String filepath;
    String genre;


    public Song(String name, String author, String genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
    public Song(String name, String author, String genre, String filepath) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.filepath = filepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return
                name + " - " + author;
    }
}
