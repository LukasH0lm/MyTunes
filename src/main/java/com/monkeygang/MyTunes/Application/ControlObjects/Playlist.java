package com.monkeygang.MyTunes.Application.ControlObjects;

import java.util.LinkedList;

public class Playlist {


    String name;

    LinkedList<Song> songList;

    public Playlist(int id, String name, LinkedList<Song> songList) {
        this.name = name;
        this.songList = songList;
    }

    public Playlist(int id, String name) {
        this.name = name;

        this.songList = new LinkedList<>();

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Song> getSongList() {
        return songList;
    }

    public void setSongList(LinkedList<Song> songList) {
        this.songList = songList;
    }

    public void addSong(Song song){
        this.songList.add(song);
    }

    public String getNumberOfSongs(){
        return String.valueOf(songList.size());
    }

    @Override
    public String toString() {
        return name;
    }
}
