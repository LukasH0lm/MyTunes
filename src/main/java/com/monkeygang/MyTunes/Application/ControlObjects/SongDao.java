package com.monkeygang.MyTunes.Application.ControlObjects;


import java.util.List;

public interface SongDao {

    public List getAllSongs();

    public void updateSongs();

    public void deleteSong();

    public void addSong();


}
